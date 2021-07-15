package com.gelato.gelatogallery.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.data.model.Images
import com.gelato.gelatogallery.reposetories.ImagesRepo
import com.gelato.gelatogallery.ui.presentation.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val imagesRepo: ImagesRepo) : BaseViewModel() {


    private var currentSearchResult: Flow<PagingData<ImageItem>>? = null

    fun getImages(): Flow<PagingData<ImageItem>> {
        val newResult: Flow<PagingData<ImageItem>> = imagesRepo.getImages(this)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}