package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class FormattedPrice(
    @SerializedName("FullPrice")
    val fullPrice: String,
    @SerializedName("DiscountedPrice")
    val discountedPrice: String,
    @SerializedName("RetailPrice")
    val retailPrice: String
)