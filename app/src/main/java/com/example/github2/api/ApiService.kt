package com.example.github2.api

import com.example.github2.response.DetailResponse
import com.example.github2.response.FollowerResponseItem
import com.example.github2.response.FollowingResponseItem
import com.example.github2.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUser(
        @Query("q")id:String
    ): Call<UserResponse>
    @GET("users/{login}")
    @Headers("Authorization: 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")
    fun getDetail(
        @Path("login")id:String
    ):Call<DetailResponse>
    @GET("users/{login}/followers")
    @Headers("Authorization: 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")
    fun getFollower(
        @Path("login")id:String
    ):Call<List<FollowerResponseItem>>
    @GET("users/{login}/following")
    @Headers("Authorization: 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")

    fun getFollowing(
        @Path("login")id:String
    ):Call<List<FollowingResponseItem>>
}