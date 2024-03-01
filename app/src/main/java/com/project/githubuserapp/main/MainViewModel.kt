package com.project.githubuserapp.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuserapp.api.RetrofitClient
import com.project.githubuserapp.models.User
import com.project.githubuserapp.models.userResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query : String) {
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<userResponse>{
                override fun onResponse(
                    call: Call<userResponse>,
                    response: Response<userResponse>
                ) {
                    if(response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<userResponse>, t: Throwable) {
                    Log.d("Farhan", "Failed get user")
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }
}