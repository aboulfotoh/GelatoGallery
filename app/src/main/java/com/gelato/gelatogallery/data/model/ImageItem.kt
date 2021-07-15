package com.gelato.gelatogallery.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class ImageItem(
    @SerializedName("author")
    var author: String = "",
    @SerializedName("download_url")
    var downloadUrl: String = "",
    @SerializedName("height")
    var height: Int = 0,
    @SerializedName("id")
    var id: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("width")
    var width: Int = 0
) : Parcelable