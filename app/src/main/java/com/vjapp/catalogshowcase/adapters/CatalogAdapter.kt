package com.vjapp.catalogshowcase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vjapp.catalogshowcase.databinding.CatalogItemBinding
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity

class CatalogAdapter(private val listener: OnCatalogItemSelectionListener) : RecyclerView.Adapter<CatalogAdapter.ListViewHolder>() {

    interface OnCatalogItemSelectionListener {
        fun onItemSelection(element: CatalogItemEntity)
    }

    private var itemsList: MutableList<CatalogItemEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CatalogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(itemsList[position], position)
    }

    override fun getItemCount(): Int {
        return this.itemsList.size
    }

    fun updateData(data: List<CatalogItemEntity>) {
        this.itemsList = data.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(data: List<CatalogItemEntity>) {
        itemsList.addAll(data.toMutableList())
        notifyDataSetChanged()
        //notifyItemRangeInserted()
    }

    fun resetData() {
        itemsList.clear()
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: CatalogItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CatalogItemEntity, position: Int) {

            binding.apply {
                tvBrand.text = data.brandName
                tvCategory.text = data.category
                tvPrice.text = data.price
                Picasso.get().load(buildUrl(data.cod10)).into(ivProduct)
            }

            itemView.setOnClickListener {
                listener.onItemSelection(data)
            }

        }

        fun buildUrl(cod10: String): String {
            return "https://cdn.yoox.biz/${cod10.substring(0..1)}/${cod10}_11_f.jpg"
        }
    }

}
