package com.serapbercin.shutterstock.ui.search.usecase

import com.serapbercin.shutterstock.OnDestroy
import com.serapbercin.shutterstock.data.imagesearch.ImageSearchDataSource
import com.serapbercin.shutterstock.ui.search.ImageSearchContract
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ImageSearchPresenter @Inject constructor(private val view: ImageSearchContract.View,
                                               private val imageSearchDataSource:
                                               ImageSearchDataSource,
                                               @OnDestroy private val onDestroyCompletable:
                                               Completable) : ImageSearchContract.Presenter {


    private var pageNumber = 2
    private var categoryId: String? = null
    private var query: String? = null

    override fun start(categoryId: String?, query: String?) {
        this.categoryId = categoryId
        this.query = query
        imageSearchDataSource.imageSearch(categoryId, query, null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showDialog() }
                .doAfterTerminate { view.hideDialog() }
                .takeUntil(onDestroyCompletable.toObservable<ImageSearchFormData>())
                .subscribe(
                        { onSuccess(it, false) },
                        { throwable -> view.showErrorMessage(throwable) })
    }

    private fun onSuccess(imageSearchFormData: ImageSearchFormData, isBoolean: Boolean) {
        view.showData(imageSearchFormData.imageSearchListData, isBoolean)
    }

    override fun listenSearchChanges() {
        view.searchImageListObservable()
                .map { ImageSearchContract.SearchImageListModel(it.query) }
                .filter({ !it.query.isNullOrEmpty() })
                .skip(1)
                .doOnNext {
                    pageNumber = 2
                    query = it.query
                    categoryId = null
                }
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap { imageSearchDataSource.imageSearch(categoryId, query, null) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showDialog() }
                .doAfterTerminate { view.hideDialog() }
                .takeUntil(onDestroyCompletable.toObservable<ImageSearchFormData>())
                .subscribe(
                        { onSuccess(it, true) },
                        { throwable -> view.showErrorMessage(throwable) })
    }

    override fun nextLoadPage() {
        imageSearchDataSource.imageSearch(categoryId, query, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showDialog() }
                .doAfterTerminate { view.hideDialog() }
                .takeUntil(onDestroyCompletable.toObservable<ImageSearchFormData>())
                .subscribe(
                        { onSuccessNextLoad(it) },
                        { throwable -> view.showErrorMessage(throwable) })

    }

    private fun onSuccessNextLoad(imageSearchFormData: ImageSearchFormData) {
        view.loadNextImageList(imageSearchFormData, false)
        pageNumber++
    }


}