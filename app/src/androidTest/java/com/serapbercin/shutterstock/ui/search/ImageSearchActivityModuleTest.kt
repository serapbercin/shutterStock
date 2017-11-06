package com.serapbercin.shutterstock.ui.search

import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.serapbercin.shutterstock.ActivityScope
import dagger.Module
import dagger.Provides


@Module
object ImageSearchActivityModuleTest {

    @JvmStatic
    @ActivityScope
    @Provides
    fun provideImageSearchPresenter(): ImageSearchContract.Presenter = mock()


    @JvmStatic
    @ActivityScope
    @Provides
    fun provideContext(imageSearchActivity: ImageSearchActivity): Context = imageSearchActivity

}