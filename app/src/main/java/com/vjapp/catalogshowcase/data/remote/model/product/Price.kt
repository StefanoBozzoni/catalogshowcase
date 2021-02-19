package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("FullPrice")
    val fullPrice: Int,
    @SerializedName("DiscountedPrice")
    val discountedPrice: Int,
    @SerializedName("RetailPrice")
    val retailPrice: Int,
    @SerializedName("DiscountedPriceEur")
    val discountedPriceEur: Int
)