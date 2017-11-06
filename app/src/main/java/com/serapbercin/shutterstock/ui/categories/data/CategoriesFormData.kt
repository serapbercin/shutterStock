package com.serapbercin.shutterstock.ui.categories.data

import com.google.gson.annotations.SerializedName


open class CategoriesFormData(@SerializedName("data") val categories: MutableList<Category>)

