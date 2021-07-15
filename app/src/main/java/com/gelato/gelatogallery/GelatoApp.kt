package com.gelato.gelatogallery

import androidx.multidex.MultiDexApplication
import com.gelato.gelatogallery.di.apiModule
import com.gelato.gelatogallery.di.networkModule
import com.gelato.gelatogallery.di.repoModule
import com.gelato.gelatogallery.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GelatoApp : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GelatoApp)
            modules(networkModule, apiModule, viewModelModule, repoModule)
        }
    }
}