package com.serapbercin.shutterstock.data.imagesearch

import com.serapbercin.shutterstock.PER_PAGE
import com.serapbercin.shutterstock.api.ShutterStockService
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ShutterStockImageSearchDataSource @Inject constructor(private val shutterStockService:
                                                            ShutterStockService) :
        ImageSearchDataSource {
    override fun imageSearch(categoryId: String?, query: String?, pageNumber: Int?):
            Observable<ImageSearchFormData> {
        return shutterStockService.search(categoryId?.toInt(), query, pageNumber, PER_PAGE)
                .subscribeOn(Schedulers.io())
                .map { it }
    }
}