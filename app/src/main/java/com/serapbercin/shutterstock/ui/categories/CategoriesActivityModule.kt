package com.serapbercin.shutterstock.ui.categories

import android.content.Context
import com.serapbercin.shutterstock.OnDestroy
import com.serapbercin.shutterstock.ActivityScope
import com.serapbercin.shutterstock.ui.categories.usecase.CategoriesPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Completable


@Module
abstract class CategoriesActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @OnDestroy
        fun provideOnDestroyCompletable(
                categoriesActivity: CategoriesActivity): Completable =
                categoriesActivity.onDestroyCompletable
    }

    @ActivityScope
    @Binds
    abstract fun bindCategoriesView(categoriesActivity: CategoriesActivity):
            CategoriesContract.View


    @ActivityScope
    @Binds
    abstract fun bindCategoriesPresenter(categoriesPresenter: CategoriesPresenter):
            CategoriesContract.Presenter


    @ActivityScope
    @Binds
    abstract fun bindContext(categoriesActivity: CategoriesActivity): Context
}