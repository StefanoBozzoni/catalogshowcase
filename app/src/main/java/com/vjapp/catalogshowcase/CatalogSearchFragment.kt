package com.vjapp.catalogshowcase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vjapp.catalogshowcase.adapters.CatalogAdapter
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity
import com.vjapp.catalogshowcase.domain.model.OperationType
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.Resource
import com.vjapp.catalogshowcase.presentation.ResourceState
import kotlinx.android.synthetic.main.fragment_catalog_search.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CatalogSearchFragment : Fragment(),
    CatalogAdapter.OnCatalogItemSelectionListener {

    private val mainViewModel : MainViewModel by sharedViewModel()
    private lateinit var endlessRecyclerViewScrollListener : EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mainViewModel.getCatalog(SearchTypes.SEARCHRESULT)
        initializeHandlers()
    }

    private fun initView() {
        val catalogAdapter = CatalogAdapter(this)
        rv_catalog_list.adapter = catalogAdapter
        val layoutManager = LinearLayoutManager(this.context)
        rv_catalog_list.layoutManager = layoutManager

        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d("DEBUG","Load more Ricette for page: ${page + 1}")
                loadData(page + 1)
            }
        }

        rv_catalog_list.addOnScrollListener(endlessRecyclerViewScrollListener )
    }

    private fun initializeHandlers() {
        mainViewModel.getCatalogLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let { handleGeCatalogComplete(response) }
        })
    }

    private fun handleGeCatalogComplete(response: Pair<Resource<CatalogEntity>,OperationType>) {

        val operation = response.second

        when (response.first.status) {
            ResourceState.LOADING -> setViewForLoading(operation)
            ResourceState.SUCCESS -> setViewForSuccess(response.first.data, response.second)
            ResourceState.ERROR -> setViewForError()
        }
    }

    private fun setViewForLoading(operation: OperationType) {
        if (operation==OperationType.REPLACE_LIST) {
            vf_catalog.displayedChild = 0
        }
        else {
            vf_catalog.displayedChild = 1
            endlessProgressBar.visibility=View.VISIBLE
        }
    }

    private fun setViewForSuccess(data: CatalogEntity?,operationType: OperationType) {
        //Fill the ricyclerview with catalog data
        if (operationType == OperationType.REPLACE_LIST)
            vf_catalog.displayedChild = 1
        else {
            endlessProgressBar.visibility=View.GONE
            vf_catalog.displayedChild = 1
        }

        //Fill the ricyclerview with catalog data
        data?.let  { newCatalog->
            //val newCatalog = data
            val catalogAdapter = rv_catalog_list.adapter as CatalogAdapter
            if (operationType==OperationType.ADD_TO_LIST)
                catalogAdapter.addData(newCatalog.catalogList)
            else {
                endlessRecyclerViewScrollListener.resetState()
                catalogAdapter.updateData(newCatalog.catalogList)
            }
        }
    }

    private fun setViewForError() {
        vf_catalog.displayedChild = 2
    }


    private fun loadData(page: Int = 1) {
        if (page == 1) {
            endlessRecyclerViewScrollListener.resetState()
            (rv_catalog_list.adapter as CatalogAdapter).resetData()
            Log.d("DEBUG","Reset to page: ${page}")
        }

        if (page%3==1)
            mainViewModel.getCatalog(SearchTypes.SEARCHRESULT, OperationType.ADD_TO_LIST)
        if (page%3==2)
            mainViewModel.getCatalog(SearchTypes.SEARCHRESULT2, OperationType.ADD_TO_LIST)
        if (page%3==0)
            mainViewModel.getCatalog(SearchTypes.SEARCHRESULT3, OperationType.ADD_TO_LIST)

    }

    override fun onItemSelection(element: CatalogItemEntity) {
        this.context?.let {
            startActivity(DetailActivity.newInstance(it))
        }
    }
}