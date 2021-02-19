package com.vjapp.catalogshowcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vjapp.catalogshowcase.adapters.CatalogAdapter
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.Resource
import com.vjapp.catalogshowcase.presentation.ResourceState
import kotlinx.android.synthetic.main.fragment_catalog_search.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CatalogSearchFragment : Fragment(),
    CatalogAdapter.OnCatalogItemSelectionListener {

    val mainViewModel : MainViewModel by sharedViewModel()

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
        mainViewModel.getCatalog()
        initializeHandlers()
    }

    private fun initializeHandlers() {
        mainViewModel.getCatalogLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let { handleGeCatalogComplete(response) }
        })
    }

    private fun handleGeCatalogComplete(response: Resource<CatalogEntity>) {
        when (response.status) {
            ResourceState.LOADING -> vf_catalog.displayedChild = 0
            ResourceState.SUCCESS -> {
                vf_catalog.displayedChild = 1
                setViewForSuccess(response.data)
            }
            ResourceState.ERROR -> vf_catalog.displayedChild = 2
        }
    }

    private fun setViewForSuccess(catalog: CatalogEntity?) {
        //Fill the ricyclerview with catalog data
        catalog?.let  {
            val catalogAdapter = rv_catalog_list.adapter as CatalogAdapter
            catalogAdapter.updateData(catalog.catalogList)
        }
    }

    private fun initView() {
        val catalogAdapter = CatalogAdapter(this)
        rv_catalog_list.adapter = catalogAdapter
        rv_catalog_list.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onItemSelection(element: CatalogItemEntity) {
        this.context?.let {
            startActivity(DetailActivity.newInstance(it))
        }
    }
}