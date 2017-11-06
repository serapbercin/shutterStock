package com.serapbercin.shutterstock.data.imagesearch

import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import io.reactivex.Observable


interface ImageSearchDataSource {
    fun imageSearch(categoryId: String?, query: String?, pageNumber: Int?): Observable<ImageSearchFormData>
}