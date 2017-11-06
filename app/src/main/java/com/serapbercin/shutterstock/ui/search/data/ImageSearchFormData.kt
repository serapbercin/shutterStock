package com.serapbercin.shutterstock.ui.search.data

import com.google.gson.annotations.SerializedName


open class ImageSearchFormData(@SerializedName("data") val imageSearchListData:
                               MutableList<ImageSearchListData> )