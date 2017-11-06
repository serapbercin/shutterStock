package com.serapbercin.shutterstock.application

import com.serapbercin.shutterstock.ActivityScope
import com.serapbercin.shutterstock.ui.categories.CategoriesActivity
import com.serapbercin.shutterstock.ui.categories.CategoriesActivityModuleTest
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class TestActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(CategoriesActivityModuleTest::class))
    abstract fun contributeAndroidInjector(): CategoriesActivity

}
