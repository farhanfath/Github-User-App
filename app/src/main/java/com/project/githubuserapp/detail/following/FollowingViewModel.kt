package com.project.githubuserapp.detail.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuserapp.api.RetrofitClient
import com.project.githubuserapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username : String) {
        RetrofitClient.apiInstance
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Farhan", "Failed Fetching Following")
                }
            })
    }

    fun getListFollowing() : LiveData<ArrayList<User>>{
        return listFollowing
    }
}
