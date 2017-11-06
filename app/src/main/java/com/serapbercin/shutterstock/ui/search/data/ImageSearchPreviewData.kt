package com.serapbercin.shutterstock.ui.search.data

import com.google.gson.annotations.SerializedName

open class ImageSearchPreviewData(@SerializedName("width") val weight: Int,
                                  @SerializedName("url") val url: String,
                                  @SerializedName("height") val height: Int)