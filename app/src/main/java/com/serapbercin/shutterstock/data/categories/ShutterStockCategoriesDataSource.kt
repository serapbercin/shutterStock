package com.serapbercin.shutterstock.data.categories

import com.serapbercin.shutterstock.api.ShutterStockService
import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ShutterStockCategoriesDataSource @Inject constructor(private val shutterStockService:
                                                           ShutterStockService) :
        CategoriesDataSource {
    override fun categories(): Observable<CategoriesFormData> {
        return shutterStockService.categories()
                .subscribeOn(Schedulers.io())
                .map { it }
    }

}