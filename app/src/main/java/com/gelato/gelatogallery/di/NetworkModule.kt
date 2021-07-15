package com.gelato.gelatogallery.di

import android.content.Context
import com.gelato.gelatogallery.network.ApiUrls
import com.gelato.gelatogallery.network.ConnectivityInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

val networkModule = module {
    single { getRetrofitClient(androidContext()) }
}
private fun getRetrofitClient(androidContext: Context) =
    Retrofit.Builder()
        .baseUrl(ApiUrls.BASE_URL)
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .addInterceptor(ConnectivityInterceptor(androidContext))
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = request.newBuilder()
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(Cache(File(androidContext.cacheDir,"okhttp_cache"), 10 * 1000 * 1000))
                .build()
        )

        .addConverterFactory(GsonConverterFactory.create())
        .build()

//private fun getApi(retrofit: Retrofit) = retrofit.create(ApiEndpointInterface::class.java)