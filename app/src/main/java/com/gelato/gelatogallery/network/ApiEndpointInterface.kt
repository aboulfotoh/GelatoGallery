package com.gelato.gelatogallery.network

import com.gelato.gelatogallery.data.model.Images
import retrofit2.http.*

interface ApiEndpointInterface {
    @GET("list")
    suspend fun getImages(@Query("page")page :Int,@Query("limit")limit:Int): Images
}