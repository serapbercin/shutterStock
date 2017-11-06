package com.serapbercin.shutterstock.api

import com.serapbercin.shutterstock.AUTHORIZATION_AUTH_VALUE
import com.serapbercin.shutterstock.ui.categories.data.CategoriesFormData
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ShutterStockService {

    @Headers(AUTHORIZATION_AUTH_VALUE)
    @GET("categories")
    fun categories(): Observable<CategoriesFormData>

    @Headers(AUTHORIZATION_AUTH_VALUE)
    @GET("search")
    fun search(@Query("category") categoryId: Int?, @Query("query") query: String?,
               @Query("page") pageNumber: Int?, @Query("per_page") perPage: Int):
            Observable<ImageSearchFormData>

}