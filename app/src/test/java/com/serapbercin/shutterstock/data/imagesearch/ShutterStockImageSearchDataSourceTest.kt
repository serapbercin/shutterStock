package com.serapbercin.shutterstock.data.imagesearch

import com.nhaarman.mockito_kotlin.whenever
import com.serapbercin.shutterstock.PER_PAGE
import com.serapbercin.shutterstock.api.ShutterStockService
import com.serapbercin.shutterstock.gsonUpper
import com.serapbercin.shutterstock.hookTestRxJavaSchedulers
import com.serapbercin.shutterstock.parseFileWith
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ShutterStockImageSearchDataSourceTest {
    private lateinit var shutterStockImageSearchDataSource: ShutterStockImageSearchDataSource
    @Mock private lateinit var shutterStockService: ShutterStockService


    @Before
    fun setUp() {
        hookTestRxJavaSchedulers()
        MockitoAnnotations.initMocks(this)
        shutterStockImageSearchDataSource = ShutterStockImageSearchDataSource(shutterStockService)
    }

    @Test
    fun loadImageSearch_whenSuccessResult() {
        val imageSearchResponse = createImageSearchResponse()
        whenever(shutterStockService.search(1, "apple", 1, PER_PAGE))
                .thenReturn(Observable.just(imageSearchResponse))

        shutterStockImageSearchDataSource.imageSearch("1", "apple", 1)
                .test().assertValues(imageSearchResponse)

    }

    private fun createImageSearchResponse(): ImageSearchFormData =
            "image_search_response_success.json" parseFileWith gsonUpper
}