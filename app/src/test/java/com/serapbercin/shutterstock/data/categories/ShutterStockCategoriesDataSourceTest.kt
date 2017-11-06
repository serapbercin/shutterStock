package com.serapbercin.shutterstock.data.categories

import com.nhaarman.mockito_kotlin.whenever
import com.serapbercin.shutterstock.api.ShutterStockService
import com.serapbercin.shutterstock.gsonUpper
import com.serapbercin.shutterstock.hookTestRxJavaSchedulers
import com.serapbercin.shutterstock.parseFileWith
import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ShutterStockCategoriesDataSourceTest {

    private lateinit var shutterStockCategoriesDataSource: ShutterStockCategoriesDataSource
    @Mock private lateinit var shutterStockService: ShutterStockService


    @Before
    fun setUp() {
        hookTestRxJavaSchedulers()
        MockitoAnnotations.initMocks(this)
        shutterStockCategoriesDataSource = ShutterStockCategoriesDataSource(shutterStockService)
    }

    @Test
    fun loadCategories_whenSuccessResult() {
        val categoriesResponse = createCategoriesResponse()
        whenever(shutterStockService.categories())
                .thenReturn(Observable.just(categoriesResponse))
        shutterStockCategoriesDataSource.categories()
                .test().assertValues(categoriesResponse)

    }

    private fun createCategoriesResponse(): CategoriesFormData =
            "categories_response_success.json" parseFileWith gsonUpper
}