package com.serapbercin.shutterstock.application

import com.serapbercin.shutterstock.ActivityScope
import com.serapbercin.shutterstock.ui.categories.CategoriesActivity
import com.serapbercin.shutterstock.ui.categories.CategoriesActivityModuleTest
import com.serapbercin.shutterstock.ui.search.ImageSearchActivity
import com.serapbercin.shutterstock.ui.search.ImageSearchActivityModuleTest
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class TestActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CategoriesActivityModuleTest::class))
    abstract fun contributeCategoriesAndroidInjector(): CategoriesActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(ImageSearchActivityModuleTest::class))
    abstract fun contributeImageSearchAndroidInjector(): ImageSearchActivity

}
