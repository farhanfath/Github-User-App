package com.project.githubuserapp.api

import com.project.githubuserapp.BuildConfig
import com.project.githubuserapp.models.DetailUserResponse
import com.project.githubuserapp.models.User
import com.project.githubuserapp.models.userResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query



const val apiKey = BuildConfig.API_KEY

interface api {

    @GET("search/users")
    @Headers("Authorization: token $apiKey")
    fun getSearchUser(
        @Query("q") query : String
    ): Call<userResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $apiKey")
    fun getUserDetail(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $apiKey")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $apiKey")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}