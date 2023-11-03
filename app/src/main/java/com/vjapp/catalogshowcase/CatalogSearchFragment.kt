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
import com.vjapp.catalogshowcase.databinding.FragmentCatalogSearchBinding
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity
import com.vjapp.catalogshowcase.domain.model.OperationType
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import com.vjapp.catalogshowcase.presentation.EspressoIdlingResource
import com.vjapp.catalogshowcase.presentation.MainViewModel
import com.vjapp.catalogshowcase.presentation.Resource
import com.vjapp.catalogshowcase.presentation.ResourceState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CatalogSearchFragment : Fragment(),
    CatalogAdapter.OnCatalogItemSelectionListener {

    private var _binding: FragmentCatalogSearchBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModel()
    private lateinit var endlessRecyclerViewScrollListener : EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mainViewModel.getCatalog(SearchTypes.SEARCHRESULT)
        initializeHandlers()
    }

    private fun initView() {
        val catalogAdapter = CatalogAdapter(this)
        binding.rvCatalogList.adapter = catalogAdapter
        val layoutManager = LinearLayoutManager(this.context)
        binding.rvCatalogList.layoutManager = layoutManager

        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d("XDEBUG","Load more Ricette for page: ${page + 1}")
                loadData(page + 1)
            }
        }

        binding.rvCatalogList.addOnScrollListener(endlessRecyclerViewScrollListener )
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
        EspressoIdlingResource.increment()
        if (operation==OperationType.REPLACE_LIST) {
            binding.vfCatalog.displayedChild = 0
        }
        else {
            binding.vfCatalog.displayedChild = 1
            binding.endlessProgressBar.visibility=View.VISIBLE
        }
    }

    private fun setViewForSuccess(data: CatalogEntity?,operationType: OperationType) {
        //Fill the ricyclerview with catalog data
        if (operationType == OperationType.REPLACE_LIST)
            binding.vfCatalog.displayedChild = 1
        else {
            binding.endlessProgressBar.visibility=View.GONE
            binding.vfCatalog.displayedChild = 1
        }

        //Fill the ricyclerview with catalog data
        data?.let  { newCatalog->
            //val newCatalog = data
            val catalogAdapter = binding.rvCatalogList.adapter as CatalogAdapter
            if (operationType==OperationType.ADD_TO_LIST)
                catalogAdapter.addData(newCatalog.catalogList)
            else {
                endlessRecyclerViewScrollListener.resetState()
                catalogAdapter.updateData(newCatalog.catalogList)
            }
            EspressoIdlingResource.decrement()
        }
    }

    private fun setViewForError() {
        binding.vfCatalog.displayedChild = 2
        EspressoIdlingResource.decrement()
    }


    private fun loadData(page: Int = 1) {
        if (page == 1) {
            endlessRecyclerViewScrollListener.resetState()
            (binding.rvCatalogList.adapter as CatalogAdapter).resetData()
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