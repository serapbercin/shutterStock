package com.serapbercin.shutterstock.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object ApiModule {

    private val API_URL = HttpUrl.parse("https://api.shutterstock.com/v2/images/")


    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()
        return Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideShutterStockService(retrofit: Retrofit): ShutterStockService {
        return retrofit.create<ShutterStockService>(ShutterStockService::class.java)
    }
}