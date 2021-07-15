package com.gelato.gelatogallery.reposetories

import androidx.paging.PagingData
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.data.model.Images
import com.gelato.gelatogallery.utils.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow

interface ImagesRepo {
    fun getImages(emitter: RemoteErrorEmitter): Flow<PagingData<ImageItem>>
}