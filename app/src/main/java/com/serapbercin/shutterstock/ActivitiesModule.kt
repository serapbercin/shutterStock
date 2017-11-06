package com.serapbercin.shutterstock


import com.serapbercin.shutterstock.ui.categories.CategoriesActivity
import com.serapbercin.shutterstock.ui.categories.CategoriesActivityModule
import com.serapbercin.shutterstock.ui.search.ImageSearchActivity
import com.serapbercin.shutterstock.ui.search.ImageSearchActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CategoriesActivityModule::class))
    abstract fun contributeCategoriesAndroidInjector(): CategoriesActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(ImageSearchActivityModule::class))
    abstract fun contributeImageSearchAndroidInjector(): ImageSearchActivity

}


