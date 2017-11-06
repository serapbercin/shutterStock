package com.serapbercin.shutterstock.ui.categories.data

import com.google.gson.annotations.SerializedName


open class Category(@SerializedName("id") val id: String,
                    @SerializedName("name") val name: String)
