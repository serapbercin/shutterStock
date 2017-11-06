package com.serapbercin.shutterstock.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.ui.search.data.ImageSearchListData
import com.squareup.picasso.Picasso
import java.util.ArrayList
import javax.inject.Inject


class ImageSearchAdapter @Inject constructor(private val picasso: Picasso,
                                             private val context: Context) :
        RecyclerView.Adapter<ImageSearchAdapter.ImageSearchViewHolder>() {

    private var imageSearchList: MutableList<ImageSearchListData>? = ArrayList()


    override fun onBindViewHolder(holder: ImageSearchViewHolder?, position: Int) {
        val imageSearchItem = imageSearchList!![position]
        holder!!.searchImageTitle.text = imageSearchItem.description
        picasso.load(imageSearchItem.assets.preview.url)
                .error(R.drawable.ic_launcher_background)
                .into(holder.searchImage)
    }


    override fun getItemCount(): Int {
        return imageSearchList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):
            ImageSearchAdapter.ImageSearchViewHolder {
        val inflater = LayoutInflater.from(context)
        return ImageSearchAdapter.ImageSearchViewHolder(inflater.inflate(R.layout.item_image_search,
                parent, false))
    }


    class ImageSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchImageTitle = itemView.findViewById<TextView>(R.id.tv_image_title)
                as TextView
        val searchImage = itemView.findViewById<ImageView>(R.id.iv_search_image)
                as ImageView
    }


    fun addAll(imageSearchList: List<ImageSearchListData>) {
        for (item in imageSearchList) {
            add(item)
        }
    }


    fun clear() {
        while (itemCount > 0) {
            remove(getItem(0)!!)
        }
    }

    private fun remove(movieItem: ImageSearchListData) {
        val position = imageSearchList!!.indexOf(movieItem)
        if (position > -1) {
            imageSearchList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun add(item: ImageSearchListData) {
        imageSearchList!!.add(item)
        notifyItemInserted(imageSearchList!!.size - 1)
    }


    private fun getItem(position: Int): ImageSearchListData? {
        return imageSearchList!![position]
    }


}