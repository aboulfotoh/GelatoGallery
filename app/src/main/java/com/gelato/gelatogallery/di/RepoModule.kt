package com.gelato.gelatogallery.di

import com.gelato.gelatogallery.reposetories.ImagesRepo
import com.gelato.gelatogallery.reposetories.ImagesRepoImpl
import org.koin.dsl.module

val repoModule = module {
    single { ImagesRepoImpl(get()) as ImagesRepo }
}