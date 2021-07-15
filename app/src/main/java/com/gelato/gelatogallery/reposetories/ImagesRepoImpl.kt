package com.gelato.gelatogallery.reposetories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gelato.gelatogallery.data.data_source.ImagesPagingSource
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.data.model.Images
import com.gelato.gelatogallery.network.ApiEndpointInterface
import com.gelato.gelatogallery.utils.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow

class ImagesRepoImpl(private val apiEndpointInterface: ApiEndpointInterface) : ImagesRepo {
    override fun getImages(emitter: RemoteErrorEmitter): Flow<PagingData<ImageItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ImagesPagingSource(apiEndpointInterface, emitter) }
        ).flow

    }
}