package com.gelato.gelatogallery.network

import android.content.Context
import com.gelato.gelatogallery.R
import com.gelato.gelatogallery.utils.UtilsHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class NoConnectivityException : IOException(), KoinComponent {

    val context: Context by inject()
    override val message: String?
        get() = UtilsHelper.getString(R.string.connectivity_exception, context)
}
