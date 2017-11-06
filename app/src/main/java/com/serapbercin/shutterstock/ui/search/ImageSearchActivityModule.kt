package com.serapbercin.shutterstock.ui.search

import android.content.Context
import com.serapbercin.shutterstock.OnDestroy
import com.serapbercin.shutterstock.ActivityScope
import com.serapbercin.shutterstock.ui.search.usecase.ImageSearchPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Completable


@Module
abstract class ImageSearchActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @OnDestroy
        fun provideOnDestroyCompletable(
                imageSearchActivity: ImageSearchActivity): Completable =
                imageSearchActivity.onDestroyCompletable
    }

    @ActivityScope
    @Binds
    abstract fun bindImageSearchView(imageSearchActivity: ImageSearchActivity):
            ImageSearchContract.View


    @ActivityScope
    @Binds
    abstract fun bindImageSearchPresenter(imageSearchPresenter: ImageSearchPresenter):
            ImageSearchContract.Presenter


    @ActivityScope
    @Binds
    abstract fun bindContext(imageSearchActivity: ImageSearchActivity): Context
}