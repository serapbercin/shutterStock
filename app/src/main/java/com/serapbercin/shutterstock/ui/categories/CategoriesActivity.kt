package com.serapbercin.shutterstock.ui.categories

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.serapbercin.shutterstock.R
import com.serapbercin.shutterstock.module.UtilityModule.Companion.DEFAULT_GSON
import com.serapbercin.shutterstock.ui.categories.data.Category
import com.serapbercin.shutterstock.ui.search.ImageSearchActivity
import com.serapbercin.shutterstock.ui.search.OPEN_FROM_CATEGORIES_PAGE_REQUEST_ID
import com.serapbercin.shutterstock.ui.search.OPEN_FROM_CATEGORIES_PAGE_WITH_SEARCH_QUERY
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.CompletableSubject
import kotterknife.bindView
import javax.inject.Inject
import javax.inject.Named

private const val STATE_CATEGORY = "category"

class CategoriesActivity : AppCompatActivity(), CategoriesContract.View {

    @field:[Inject Named(DEFAULT_GSON)] lateinit var gson: Gson
    @Inject lateinit var presenter: CategoriesContract.Presenter
    @Inject lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val categoriesView: RecyclerView by bindView(R.id.rv_categories)
    private val searchViewToolbar: SearchView by bindView(R.id.sv_toolbar)
    private val progressBar: ProgressBar by bindView(R.id.pb_load_categories)
    private val rootView: RelativeLayout by bindView(R.id.rl_root_view)

    val onDestroyCompletable = CompletableSubject.create()!!
    private var categories = emptyList<Category>()


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_categories)

        initRecyclerViewAndAdapter()
        setupSearchAction()
        if (savedInstanceState != null) {
            savingAndReloadCategoryList(savedInstanceState)
        } else {
            presenter.start()
        }
        presenter.listenCategoryItemClicks()
    }

    private fun savingAndReloadCategoryList(savedInstanceState: Bundle) {
        val categoriesJSon = savedInstanceState.getString(STATE_CATEGORY)
        val listType = object : TypeToken<MutableList<Category>>() {}.type
        categories = gson.fromJson<MutableList<Category>>(categoriesJSon, listType)
        categoriesAdapter.update(gson.fromJson<MutableList<Category>>(categoriesJSon, listType))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val listType = object : TypeToken<MutableList<Category>>() {}.type
        outState!!.putSerializable(STATE_CATEGORY, gson.toJson(categories, listType))
        super.onSaveInstanceState(outState)
    }


    private fun initRecyclerViewAndAdapter() {
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false)
        val dividerItemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)

        categoriesView.layoutManager = linearLayoutManager
        categoriesView.addItemDecoration(dividerItemDecoration)
        categoriesView.setHasFixedSize(true)
        categoriesView.adapter = categoriesAdapter
    }

    private fun setupSearchAction() {
        val intent = Intent(this, ImageSearchActivity::class.java)

        val cn = ComponentName(this, CategoriesActivity::class.java)
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchViewToolbar.queryHint = this.resources.getString(R.string.search_image)
        searchViewToolbar.setSearchableInfo(searchManager.getSearchableInfo(cn))
        searchViewToolbar.setIconifiedByDefault(false)

        searchViewToolbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                intent.putExtra(OPEN_FROM_CATEGORIES_PAGE_WITH_SEARCH_QUERY, query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun showDialog() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideDialog() {
        progressBar.visibility = View.GONE
    }

    override fun showErrorMessage(throwable: Throwable) {
        Snackbar.make(rootView, throwable.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    override fun showCategories(categories: MutableList<Category>) {
        this.categories = categories
        categoriesAdapter.update(categories)
    }


    override fun listItemClicks(): Observable<String> {
        return categoriesAdapter.listItemClicks
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun navigateImageSearchActivity(categoryId: String) {
        val intent = Intent(this, ImageSearchActivity::class.java)
        intent.putExtra(OPEN_FROM_CATEGORIES_PAGE_REQUEST_ID, categoryId)
        startActivity(intent)
    }

    override fun navigateImageSearchWithQuery(query: String?) {
        val intent = Intent(this, ImageSearchActivity::class.java)
        intent.putExtra(OPEN_FROM_CATEGORIES_PAGE_WITH_SEARCH_QUERY, query)
        startActivity(intent)
    }

}

