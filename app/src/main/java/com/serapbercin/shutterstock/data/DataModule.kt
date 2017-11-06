package com.serapbercin.shutterstock.data

import com.serapbercin.shutterstock.api.ApiModule
import com.serapbercin.shutterstock.data.categories.CategoriesDataSource
import com.serapbercin.shutterstock.data.categories.ShutterStockCategoriesDataSource
import com.serapbercin.shutterstock.data.imagesearch.ImageSearchDataSource
import com.serapbercin.shutterstock.data.imagesearch.ShutterStockImageSearchDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module(includes = arrayOf(ApiModule::class))
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCategoriesDataSource(shutterStockCategoriesDataSource:
                                          ShutterStockCategoriesDataSource): CategoriesDataSource


    @Binds
    @Singleton
    abstract fun bindImageSearchDataSource(shutterStockImageSearchDataSource:
                                           ShutterStockImageSearchDataSource): ImageSearchDataSource
}