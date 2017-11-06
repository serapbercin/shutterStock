package com.serapbercin.shutterstock.ui.categories

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.ui.categories.data.Category
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.ArrayList
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(private val context: Context) :
        RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categories: MutableList<Category>? = ArrayList()
    private val onListItemClickSubject = PublishSubject.create<String>()
    val listItemClicks: Observable<String>
        get() = onListItemClickSubject

    override fun onBindViewHolder(holder: CategoriesViewHolder?, position: Int) {
        val category = categories!![position]
        holder!!.categoryTitle.text = category.name

        RxView.clicks(holder.itemView).map { category.id }.subscribe(onListItemClickSubject)
    }


    override fun getItemCount(): Int {
        return categories!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):
            CategoriesAdapter.CategoriesViewHolder {
        val inflater = LayoutInflater.from(context)
        return CategoriesAdapter.CategoriesViewHolder(inflater.inflate(R.layout.item_category,
                parent, false))
    }


    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTitle = itemView.findViewById<TextView>(R.id.tv_category_id)
                as TextView
    }

    fun update(categories: MutableList<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }


}