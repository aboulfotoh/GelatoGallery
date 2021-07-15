package com.gelato.gelatogallery.ui.presentation

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gelato.gelatogallery.network.ApiEndpointInterface
import com.gelato.gelatogallery.utils.ErrorType
import com.gelato.gelatogallery.utils.RemoteErrorEmitter
import com.gelato.gelatogallery.utils.ScreenState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel : ViewModel(), KoinComponent, RemoteErrorEmitter {

    val context: Context by inject()
    val mutableProgress = MutableLiveData(View.GONE)
    val mutableScreenState = MutableLiveData<ScreenState>().apply { value = ScreenState.RENDER }
    private val mutableErrorMessage = MutableLiveData<String>()
    val mutableSuccessMessage = MutableLiveData<String>()
    val mutableErrorType = MutableLiveData<ErrorType>()

    override fun onError(errorType: ErrorType) {
        mutableErrorType.postValue(errorType)
    }

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(msg)
    }
}