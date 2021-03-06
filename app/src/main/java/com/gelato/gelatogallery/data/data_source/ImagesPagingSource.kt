package com.gelato.gelatogallery.data.data_source

import androidx.paging.PagingState
import com.gelato.gelatogallery.data.model.ImageItem
import com.gelato.gelatogallery.data.model.Images
import com.gelato.gelatogallery.network.ApiEndpointInterface
import com.gelato.gelatogallery.reposetories.ImagesRepo
import com.gelato.gelatogallery.utils.ErrorType
import com.gelato.gelatogallery.utils.RemoteErrorEmitter
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException

private const val STARTING_PAGE_INDEX = 1

class ImagesPagingSource(private val serviceApi: ApiEndpointInterface,private val emitter: RemoteErrorEmitter): BasePagingSource<Int,ImageItem>() {
    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        return state.anchorPosition?.let {
                anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        val position = (params.key ?: STARTING_PAGE_INDEX)
        return try {
            val response =
                serviceApi.getImages(position, params.loadSize)

                val nextKey = if (response.isEmpty()) {
                    null
                } else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + (params.loadSize / 10)
                }
                LoadResult.Page(
                    data = response,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 401) emitter.onError(ErrorType.SESSION_EXPIRED)
                    else {
                        val body = e.response()?.errorBody()
                        emitter.onError(getErrorMessage(body))
                    }
                }
                is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                is java.io.IOException -> emitter.onError(ErrorType.NETWORK)
                else -> emitter.onError(ErrorType.UNKNOWN)
            }
            return LoadResult.Error(e)
        }
    }
}