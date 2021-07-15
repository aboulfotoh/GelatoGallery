package com.gelato.gelatogallery.utils

import com.gelato.gelatogallery.utils.ErrorType

/**
 * Created by eagskunst in 1/12/2019.
 */
interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}