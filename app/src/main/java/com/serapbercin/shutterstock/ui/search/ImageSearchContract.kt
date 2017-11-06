package com.serapbercin.shutterstock.ui.search

import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import com.serapbercin.shutterstock.ui.search.data.ImageSearchListData
import io.reactivex.Observable

interface ImageSearchContract {

    interface View {
        fun showDialog()
        fun hideDialog()
        fun showErrorMessage(throwable: Throwable)
        fun showData(imageSearchListData: MutableList<ImageSearchListData>, isSearch: Boolean)
        fun loadNextImageList(imageSearchFormData: ImageSearchFormData, isSearch: Boolean)
        fun searchImageListObservable(): Observable<ImageSearchContract.SearchImageListModel>
    }


    interface Presenter {
        fun start(categoryId: String?, query: String?)
        fun nextLoadPage()
        fun listenSearchChanges()
    }


    data class SearchImageListModel(val query: String?)
}