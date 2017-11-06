package com.serapbercin.shutterstock.ui.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.module.UtilityModule
import com.serapbercin.shutterstock.ui.categories.CategoriesActivity
import com.serapbercin.shutterstock.ui.categories.data.Category
import com.serapbercin.shutterstock.ui.search.data.ImageSearchFormData
import com.serapbercin.shutterstock.ui.search.data.ImageSearchListData
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.CompletableSubject
import kotterknife.bindView
import javax.inject.Inject
import javax.inject.Named


private const val ITEM_COUNT = 10
const val OPEN_FROM_CATEGORIES_PAGE_REQUEST_ID = "categoryRequestId"
const val OPEN_FROM_CATEGORIES_PAGE_WITH_SEARCH_QUERY = "searchQuery"
private const val STATE_IMAGE_SEARCH = "imageSearch"

class ImageSearchActivity : AppCompatActivity(), ImageSearchContract.View {

    @field:[Inject Named(UtilityModule.DEFAULT_GSON)] lateinit var gson: Gson
    @Inject lateinit var presenter: ImageSearchContract.Presenter
    @Inject lateinit var imageSearchAdapter: ImageSearchAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val imageSearchView: RecyclerView by bindView(R.id.rv_image_search)
    private val searchViewToolbar: SearchView by bindView(R.id.sv_toolbar)
    private val progressBar: ProgressBar by bindView(R.id.pb_load_image)
    private val rootView: RelativeLayout by bindView(R.id.rl_root_view)


    val onDestroyCompletable = CompletableSubject.create()!!
    private var lastPage = false
    private var imageSearchList = emptyList<ImageSearchListData>()
    private lateinit var imageSearchObservable: Observable<ImageSearchContract.SearchImageListModel>


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_image_search)
        initRecyclerViewAndAdapter()
        setupSearchAction()
        val categoryId = intent.getStringExtra(OPEN_FROM_CATEGORIES_PAGE_REQUEST_ID)
        val query = intent.getStringExtra(OPEN_FROM_CATEGORIES_PAGE_WITH_SEARCH_QUERY)


        if (savedInstanceState != null) {
            savingAndReloadImageSearchList(savedInstanceState)
        } else {
            presenter.start(categoryId, query)
        }

        presenter.listenSearchChanges()
    }


    override fun showData(imageSearchListData: MutableList<ImageSearchListData>, isSearch: Boolean) {
        this.imageSearchList = imageSearchListData


        if (isSearch) {
            imageSearchView.clearOnScrollListeners()
        }
        imageSearchAdapter.clear()
        imageSearchAdapter.addAll(imageSearchList)

        if (imageSearchList.isEmpty()) {
            lastPage = true
        } else if (imageSearchList.size < ITEM_COUNT) {
            lastPage = true
        } else {
            lastPage = false
            if (isSearch) {
                pagingScrollListener()
            }
        }
    }


    override fun loadNextImageList(imageSearchFormData: ImageSearchFormData, isSearch: Boolean) {

        val imageSearchList = imageSearchFormData.imageSearchListData
        lastPage = when {
            imageSearchList.isEmpty() -> true
            imageSearchList.size < ITEM_COUNT -> {
                imageSearchAdapter.addAll(imageSearchList)
                true
            }
            else -> {
                imageSearchAdapter.addAll(imageSearchList)
                false
            }
        }
    }

    override fun searchImageListObservable():
            Observable<ImageSearchContract.SearchImageListModel> = imageSearchObservable

    override fun showDialog() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideDialog() {
        progressBar.visibility = View.GONE
    }

    override fun showErrorMessage(throwable: Throwable) {
        Snackbar.make(rootView, throwable.message.toString(), Snackbar.LENGTH_SHORT).show()
    }


    private fun pagingScrollListener() {
        imageSearchView.addOnScrollListener(object : PagingScrollListener(linearLayoutManager) {
            override val isLastPage: Boolean
                get() = lastPage

            override fun loadMoreItems() {
                presenter.nextLoadPage()
            }
        })
    }

    private fun savingAndReloadImageSearchList(savedInstanceState: Bundle) {
        val imagesJSon = savedInstanceState.getString(STATE_IMAGE_SEARCH)
        val listType = object : TypeToken<MutableList<Category>>() {}.type
        imageSearchList = gson.fromJson<MutableList<ImageSearchListData>>(imagesJSon, listType)
        imageSearchAdapter.addAll(gson.fromJson<MutableList<ImageSearchListData>>(imagesJSon, listType))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val listType = object : TypeToken<MutableList<Category>>() {}.type
        outState!!.putSerializable(STATE_IMAGE_SEARCH, gson.toJson(imageSearchList, listType))
        super.onSaveInstanceState(outState)
    }


    private fun initRecyclerViewAndAdapter() {
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false)
        val dividerItemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)

        imageSearchView.layoutManager = linearLayoutManager
        imageSearchView.addItemDecoration(dividerItemDecoration)
        imageSearchView.setHasFixedSize(true)
        imageSearchView.adapter = imageSearchAdapter
        pagingScrollListener()
    }

    private fun setupSearchAction() {
        val cn = ComponentName(this, CategoriesActivity::class.java)
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchViewToolbar.queryHint = this.resources.getString(R.string.search_image)
        searchViewToolbar.setSearchableInfo(searchManager.getSearchableInfo(cn))
        searchViewToolbar.setIconifiedByDefault(false)

        imageSearchObservable = RxSearchView.queryTextChanges(searchViewToolbar)
                .map { ImageSearchContract.SearchImageListModel(it.toString()) }
                .subscribeOn(AndroidSchedulers.mainThread())

    }

}