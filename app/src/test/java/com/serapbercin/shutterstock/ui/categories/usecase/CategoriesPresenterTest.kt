package com.serapbercin.shutterstock.ui.categories.usecase

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.serapbercin.shutterstock.data.categories.CategoriesDataSource
import com.serapbercin.shutterstock.gsonUpper
import com.serapbercin.shutterstock.hookTestRxJavaSchedulers
import com.serapbercin.shutterstock.parseFileWith
import com.serapbercin.shutterstock.ui.categories.CategoriesContract
import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import io.reactivex.Observable
import io.reactivex.subjects.CompletableSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class CategoriesPresenterTest {

    @Mock lateinit var categoryContractView: CategoriesContract.View
    @Mock lateinit var categoryDataSource: CategoriesDataSource
    private lateinit var onDestroyCompletable: CompletableSubject
    private lateinit var categoriesPresenter: CategoriesPresenter

    @Before
    fun setUp() {
        hookTestRxJavaSchedulers()
        MockitoAnnotations.initMocks(this)
        onDestroyCompletable = CompletableSubject.create()
        categoriesPresenter = CategoriesPresenter(categoryContractView, categoryDataSource,
                onDestroyCompletable)
    }

    @Test
    fun categoryList_whenCategoryListObserved_thenSubscribeShowList() {
        val categoriesResponse =
                createCategoriesResponse()
        whenever(categoryDataSource.categories())
                .thenReturn(Observable.just(categoriesResponse))
        categoriesPresenter.start()
        verify(categoryContractView).showDialog()
        verify(categoryContractView).hideDialog()
        verify(categoryContractView).showCategories(categoriesResponse.categories)
    }

    @Test
    fun categoryList_whenCategoryListThrowException_thenCategoryListShowExceptionMessage() {
        val throwable = Throwable("Error")
        whenever(categoryDataSource.categories())
                .thenReturn(Observable.error(throwable))
        categoriesPresenter.start()
        verify(categoryContractView).showDialog()
        verify(categoryContractView).hideDialog()
        verify(categoryContractView).showErrorMessage(throwable)

    }


    @Test
    fun categoryList_whenClickItemList_thenNavigateImageSearch() {
        whenever(categoryContractView.listItemClicks())
                .thenReturn(Observable.just("noword"))
        categoriesPresenter.listenCategoryItemClicks()
        verify(categoryContractView).navigateImageSearchActivity("noword")
    }


    private fun createCategoriesResponse():
            CategoriesFormData = "categories_response_success.json" parseFileWith gsonUpper


}