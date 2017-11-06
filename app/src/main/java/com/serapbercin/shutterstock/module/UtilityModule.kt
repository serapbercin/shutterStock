package com.serapbercin.shutterstock.module


import com.google.gson.Gson
import com.jakewharton.picasso.OkHttp3Downloader
import com.serapbercin.shutterstock.BuildConfig
import com.serapbercin.shutterstock.ShutterStockApplication
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@Module
class UtilityModule {
    companion object {
        const val DEFAULT_GSON = "defaultGson"
    }

    @Provides
    @Singleton
    fun providePicasso(shutterStockApplication: ShutterStockApplication): Picasso {
        return Picasso.Builder(shutterStockApplication).loggingEnabled(BuildConfig.DEBUG)
                .downloader(OkHttp3Downloader(OkHttpClient()))
                .executor(Executors.newSingleThreadExecutor())
                .build()
    }

    @Provides
    @Singleton
    @Named(DEFAULT_GSON)
    fun provideDefaultGson(): Gson {
        return Gson()
    }
}