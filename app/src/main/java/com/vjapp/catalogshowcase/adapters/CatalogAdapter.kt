package com.vjapp.catalogshowcase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vjapp.catalogshowcase.R
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity
import kotlinx.android.synthetic.main.catalog_item.view.*

class CatalogAdapter(private val listener: OnCatalogItemSelectionListener) : RecyclerView.Adapter<CatalogAdapter.ListViewHolder>() {

    interface OnCatalogItemSelectionListener {
        fun onItemSelection(element: CatalogItemEntity)
        fun onEmptyList() {}
    }

    private var itemsList: MutableList<CatalogItemEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(itemsList[position],position)
    }

    override fun getItemCount(): Int {
        return this.itemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.catalog_item
    }


    fun updateData(data: List<CatalogItemEntity>) {
        this.itemsList = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: CatalogItemEntity, position:Int) {

            with (itemView) {
                tvBrand.text = data.brandName
                tvCategory.text = data.category
                tvPrice.text = data.price
                Picasso.get().load(buildUrl(data.cod10)).into(ivProduct)
            }

            itemView.setOnClickListener {
                listener.onItemSelection(data)
            }

        }

        fun buildUrl(cod10:String):String {
            return "https://cdn.yoox.biz/${cod10.substring(0..1)}/${cod10}_11_f.jpg"
        }
    }

}
