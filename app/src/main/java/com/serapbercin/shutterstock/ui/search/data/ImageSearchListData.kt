package com.serapbercin.shutterstock.ui.search.data

import com.google.gson.annotations.SerializedName

open class ImageSearchListData(@SerializedName("id") val id: String,
                               @SerializedName("aspect") val aspect: Float,
                               @SerializedName("assets") val assets: ImageSearchAssetsData,
                               @SerializedName("description") val description: String,
                               @SerializedName("image_type") val imageType: String)