package com.serapbercin.shutterstock.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.serapbercin.shutterstock.module.UtilityModule.Companion.DEFAULT_GSON
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UtilityModuleTest {

    @Provides
    @Singleton
    fun providePicasso(): Picasso {
        val picasso: Picasso = mock()
        val reqCreatorMock: RequestCreator = mock()
        whenever(picasso.load(any<String>())).thenReturn(reqCreatorMock)
        whenever(reqCreatorMock.resize(any(), any())).thenReturn(reqCreatorMock)
        whenever(reqCreatorMock.error(any<Int>())).thenReturn(reqCreatorMock)
        return picasso
    }


    @Provides
    @Singleton
    @Named(DEFAULT_GSON)
    fun provideGson(): Gson = GsonBuilder().create()
}
