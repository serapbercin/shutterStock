package com.serapbercin.shutterstock.ui.search

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager

abstract class PagingScrollListener(private val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView!!.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!isLastPage && !loading &&
                    totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                loadMoreItems()
                loading = true
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val isLastPage: Boolean
}