package com.uchi.ditty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceAPI {
    @GET("videos")
    fun getVideos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<ApiResponse>
}