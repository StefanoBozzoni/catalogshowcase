package com.vjapp.catalogshowcase.data.remote.model.catalog


import com.google.gson.annotations.SerializedName

data class Analytics(
    @SerializedName("SelectedBrand")
    val selectedBrand: String,
    @SerializedName("TypedTextSearch")
    val typedTextSearch: String,
    @SerializedName("TotalPages")
    val totalPages: String,
    @SerializedName("Color")
    val color: String,
    @SerializedName("Season")
    val season: String,
    @SerializedName("ItemPerPage")
    val itemPerPage: String,
    @SerializedName("SelectedPage")
    val selectedPage: String,
    @SerializedName("TotalItems")
    val totalItems: String,
    @SerializedName("Url")
    val url: String
)