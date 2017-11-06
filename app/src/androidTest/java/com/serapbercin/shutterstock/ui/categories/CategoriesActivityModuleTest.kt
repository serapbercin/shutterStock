package com.serapbercin.shutterstock.ui.categories

import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.serapbercin.shutterstock.ActivityScope
import dagger.Module
import dagger.Provides


@Module
object CategoriesActivityModuleTest {

    @JvmStatic
    @ActivityScope
    @Provides
    fun provideCategoriesPresenter(): CategoriesContract.Presenter = mock()



    @JvmStatic
    @ActivityScope
    @Provides
    fun provideContext(categoriesActivity: CategoriesActivity): Context = categoriesActivity
}
