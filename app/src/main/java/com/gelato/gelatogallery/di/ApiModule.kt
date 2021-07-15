package com.gelato.gelatogallery.di

import com.gelato.gelatogallery.network.ApiEndpointInterface
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module{
    single(createdAtStart = false) { get<Retrofit>().create(ApiEndpointInterface::class.java) }
}