package com.serapbercin.shutterstock.ui.categories.usecase

import com.serapbercin.shutterstock.OnDestroy
import com.serapbercin.shutterstock.data.categories.CategoriesDataSource
import com.serapbercin.shutterstock.ui.categories.CategoriesContract
import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class CategoriesPresenter @Inject constructor(private val categoriesView: CategoriesContract.View,
                                              private val categoriesDataSource: CategoriesDataSource,
                                              @OnDestroy private val onDestroyCompletable: Completable)
    : CategoriesContract.Presenter {


    override fun start() {
        categoriesDataSource.categories()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { categoriesView.showDialog() }
                .doAfterTerminate { categoriesView.hideDialog() }
                .takeUntil(onDestroyCompletable.toObservable<CategoriesFormData>())
                .subscribe(
                        { onSuccess(it) },
                        { throwable -> categoriesView.showErrorMessage(throwable) })
    }

    private fun onSuccess(categoriesFormData: CategoriesFormData) {
        categoriesView.showCategories(categoriesFormData.categories)
    }


    override fun listenCategoryItemClicks() {
        categoriesView.listItemClicks()
                .filter({ !it.isEmpty() })
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(onDestroyCompletable.toObservable<String>())
                .subscribe({ categoriesView.navigateImageSearchActivity(it) })
    }

}