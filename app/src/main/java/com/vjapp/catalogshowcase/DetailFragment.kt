package com.vjapp.catalogshowcase

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
import com.vjapp.catalogshowcase.databinding.FragmentDetail2Binding
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.Resource
import com.vjapp.catalogshowcase.presentation.ResourceState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by activityViewModel()

    private var _binding: FragmentDetail2Binding? = null
    private val binding get() = _binding!!

    private val array_color_fab = intArrayOf(
        R.id.fab_color_1,
        R.id.fab_color_2,
        R.id.fab_color_3,
        R.id.fab_color_4
    )
    var choosenColor :Int = -1
    private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedState = savedInstanceState
        System.out.println("---->XDEBUG rilancio il fragment")
        detailViewModel.getProduct()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetail2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initializeHandlers()
    }

    fun initView() {
        array_color_fab.forEach { idFab -> view?.findViewById<FloatingActionButton>(idFab)?.setOnClickListener {v->setColor(v)} }
    }

    private fun initializeHandlers() {
        detailViewModel.getProductLiveDataState().observe(viewLifecycleOwner, Observer { response ->
            response?.let { handleGetProductComplete(response) }
        })
    }

    private fun handleGetProductComplete(response: Resource<ProductEntity>) {
        when (response.status) {
            ResourceState.LOADING -> {
                binding.vfProduct.displayedChild = 0
                System.out.println("-----> XDEBUG dentro loading")
            }
            ResourceState.SUCCESS -> {
                setViewForSuccess(response.data)
                binding.vfProduct.displayedChild = 1
            }
            ResourceState.ERROR -> {
                System.out.println("-----> XDEBUG dentro error")
                binding.vfProduct.displayedChild = 2
            }
        }
    }

    private fun setViewForSuccess(product: ProductEntity?) {
        //Fill the ricyclerview with catalog data
        System.out.println("-----> XDEBUG dentro success")
        product?.let {
            binding.detailForm.apply {
                tvBrand.text = it.brandName
                tvCategory.text = it.category
                tvPrice.text = it.price
                context?.let { context->
                    Picasso.get().load(buildUrl(it.cod10)).into(ivProductItem)
                }
                val descrizione = it.itemDescriptions.productInfo.joinToString(separator = "\n")
                tvDescription.text = descrizione
            }
        }

        //fills the color picker
        product?.colors?.forEachIndexed { index,_->
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
        binding.detailForm.spSizes.adapter = sizesAdapter
    }

    fun setColor(v: View) {
        (v as FloatingActionButton).setImageResource(R.drawable.ic_done)
        array_color_fab.forEachIndexed { idx, id ->
            if (v.getId() != id) {
                (view?.findViewById<View>(id) as FloatingActionButton).setImageResource(
                    android.R.color.transparent
                )

            }
            else choosenColor=idx
        }
    }

    fun buildUrl(cod10: String): String {
        return "https://cdn.yoox.biz/${cod10.substring(0..1)}/${cod10}_11_f.jpg"
    }


    private var savedState: Bundle? = null

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedState ?: savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(savedState ?: outState)
    }
}