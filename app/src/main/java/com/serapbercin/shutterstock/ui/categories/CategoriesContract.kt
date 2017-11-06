package com.serapbercin.shutterstock.ui.categories

import com.serapbercin.shutterstock.ui.categories.data.Category
import io.reactivex.Observable


interface CategoriesContract {

    interface View {
        fun showDialog()
        fun hideDialog()
        fun showErrorMessage(throwable: Throwable)
        fun showCategories(categories: MutableList<Category>)
        fun listItemClicks(): Observable<String>
        fun navigateImageSearchActivity(categoryId: String)
        fun navigateImageSearchWithQuery(query: String?)
    }

    interface Presenter {
        fun start()
        fun listenCategoryItemClicks()
    }


}