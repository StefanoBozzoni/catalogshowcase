package com.vjapp.catalogshowcase.domain.model


import com.google.gson.annotations.SerializedName

data class ItemDescriptionsEntity(
    @SerializedName("SizeInfo")
    val sizeInfo: String,
    @SerializedName("ProductInfo")
    val productInfo: List<String>,
    @SerializedName("BrandInfo")
    val brandInfo: String
)