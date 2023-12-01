package com.example.githubusersubmission.data.retrofit

import com.example.githubusersubmission.data.response.DetailUserResponse
import com.example.githubusersubmission.data.response.GithubResponse
import com.example.githubusersubmission.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") username: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}