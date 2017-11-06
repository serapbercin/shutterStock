package com.serapbercin.shutterstock.ui.search.usecase

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.serapbercin.shutterstock.data.imagesearch.ImageSearchDataSource
import com.serapbercin.shutterstock.gsonUpper
import com.serapbercin.shutterstock.hookTestRxJavaSchedulers
import com.serapbercin.shutterstock.parseFileWith
import com.serapbercin.shutterstock.ui.search.ImageSearchContract
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import io.reactivex.Observable
import io.reactivex.subjects.CompletableSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class ImageSearchPresenterTest {


    @Mock lateinit var imageSearchContractView: ImageSearchContract.View
    @Mock lateinit var imageSearchDataSource: ImageSearchDataSource
    private lateinit var onDestroyCompletable: CompletableSubject
    private lateinit var imageSearchPresenter: ImageSearchPresenter

    @Before
    fun setUp() {
        hookTestRxJavaSchedulers()
        MockitoAnnotations.initMocks(this)
        onDestroyCompletable = CompletableSubject.create()
        imageSearchPresenter = ImageSearchPresenter(imageSearchContractView, imageSearchDataSource,
                onDestroyCompletable)
    }

    @Test
    fun imageSearchList_whenImageSearchObserved_thenSubscribeShowList() {
        val imageSearchResponse =
                createImageSearchResponse()
        whenever(imageSearchDataSource.imageSearch("1", "apple", null))
                .thenReturn(Observable.just(imageSearchResponse))
        imageSearchPresenter.start("1", "apple")
        verify(imageSearchContractView).showDialog()
        verify(imageSearchContractView).hideDialog()
        verify(imageSearchContractView).showData(imageSearchResponse.imageSearchListData, false)
    }

    @Test
    fun imageSearchList_whenImageSearchThrowException_thenImageSearchListShowExceptionMessage() {
        val throwable = Throwable("Error")
        whenever(imageSearchDataSource.imageSearch("1", "apple", null))
                .thenReturn(Observable.error(throwable))
        imageSearchPresenter.start("1", "apple")
        verify(imageSearchContractView).showDialog()
        verify(imageSearchContractView).hideDialog()
        verify(imageSearchContractView).showErrorMessage(throwable)

    }

    @Test
    fun imageSearchList_whenSearchKeyWordChanged_ThenUpdateList() {
        whenever(imageSearchContractView.searchImageListObservable())
                .thenReturn(Observable.just(ImageSearchContract.SearchImageListModel("apple")))
        imageSearchPresenter.listenSearchChanges()
        verify(imageSearchContractView).searchImageListObservable()
        verify(imageSearchContractView).showDialog()
        verify(imageSearchContractView).hideDialog()
    }

    @Test
    fun imageSearchList_whenNextLoadPage_ThenFetchNewPage(){
        val imageSearchResponse =
                createImageSearchResponse()
        whenever(imageSearchDataSource.imageSearch(null, null, 2))
                .thenReturn(Observable.just(imageSearchResponse))
        imageSearchPresenter.nextLoadPage()
        verify(imageSearchContractView).showDialog()
        verify(imageSearchContractView).hideDialog()
        verify(imageSearchContractView).loadNextImageList(imageSearchResponse, false)
    }

    private fun createImageSearchResponse():
            ImageSearchFormData = "image_search_response_success.json" parseFileWith gsonUpper


}