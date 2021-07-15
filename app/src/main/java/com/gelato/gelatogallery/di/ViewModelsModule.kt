package com.gelato.gelatogallery.di

import com.gelato.gelatogallery.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(imagesRepo = get())
    }
}