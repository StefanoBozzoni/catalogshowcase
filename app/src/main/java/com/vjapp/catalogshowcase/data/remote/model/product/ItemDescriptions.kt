package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class ItemDescriptions(
    @SerializedName("SizeInfo")
    val sizeInfo: String,
    @SerializedName("ProductInfo")
    val productInfo: List<String>,
    @SerializedName("BrandInfo")
    val brandInfo: String
)