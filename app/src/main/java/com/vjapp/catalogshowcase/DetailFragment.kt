package com.vjapp.catalogshowcase

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.Resource
import com.vjapp.catalogshowcase.presentation.ResourceState
import kotlinx.android.synthetic.main.fragment_detail2.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by sharedViewModel()

    private val array_color_fab = intArrayOf(
        R.id.fab_color_1,
        R.id.fab_color_2,
        R.id.fab_color_3,
        R.id.fab_color_4
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        detailViewModel.getProduct()
        initializeHandlers()
    }

    fun initView() {
        array_color_fab.forEach { idFab -> view?.findViewById<FloatingActionButton>(idFab)?.setOnClickListener {v->setColor(v)} }
    }

    private fun initializeHandlers() {
        detailViewModel.getProductLiveData.observe(viewLifecycleOwner, Observer { response ->
            response?.let { handleGetProductComplete(response) }
        })
    }

    private fun handleGetProductComplete(response: Resource<ProductEntity>) {
        when (response.status) {
            //ResourceState.LOADING -> vf_catalog.displayedChild = 0
            ResourceState.SUCCESS -> {
                //vf_catalog.displayedChild = 1
                setViewForSuccess(response.data)
            }
            else -> {}
            //ResourceState.ERROR -> vf_catalog.displayedChild = 2
        }
    }

    private fun setViewForSuccess(product: ProductEntity?) {
        //Fill the ricyclerview with catalog data
        product?.let {
            tvBrand.text = product.brandName
            tvCategory.text = product.category
            tvPrice.text = product.price
            Picasso.get().load(buildUrl(product.cod10)).into(ivProductItem)
            val descrizione = product.itemDescriptions.productInfo.joinToString(separator = "\n")
            tvDescription.text = descrizione
        }

        //fills the color picker
        product?.colors?.forEachIndexed { index,color->
            val rgbValue = "#"+product.colors[index].rgb
            val colorRgb = Color.parseColor(rgbValue)
            view?.findViewById<FloatingActionButton>(array_color_fab[index])?.backgroundTintList = ColorStateList.valueOf(colorRgb)
        }

        val numcolors = product?.colors?.size?:0
        for (i in numcolors..3) {
            view?.findViewById<FloatingActionButton>(array_color_fab[i])?.visibility=View.INVISIBLE
        }

        //fills the sizes spinner
        val sizesAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, product?.sizes?.map { v -> v.name }?: emptyList())

        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSizes.adapter = sizesAdapter


    }

    fun setColor(v: View) {
        (v as FloatingActionButton).setImageResource(R.drawable.ic_done)
        for (id in array_color_fab) {
            if (v.getId() != id) {
                (view?.findViewById<View>(id) as FloatingActionButton).setImageResource(
                    android.R.color.transparent
                )
            }
        }
    }

    fun buildUrl(cod10: String): String {
        return "https://cdn.yoox.biz/${cod10.substring(0..1)}/${cod10}_11_f.jpg"
    }

}