package com.serapbercin.shutterstock.data.categories

import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import io.reactivex.Observable

interface CategoriesDataSource {
    fun categories(): Observable<CategoriesFormData>
}