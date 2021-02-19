package com.vjapp.catalogshowcase.data.remote.model.catalog


import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("Tags")
    val tags: List<Any>,
    @SerializedName("Department")
    val department: String,
    @SerializedName("InheritedMicroDesignerIds")
    val inheritedMicroDesignerIds: String,
    @SerializedName("SortId")
    val sortId: Int,
    @SerializedName("SortValue")
    val sortValue: String,
    @SerializedName("Season")
    val season: String,
    @SerializedName("Gender")
    val gender: String,
    @SerializedName("Page")
    val page: Int,
    @SerializedName("ProductsPerPage")
    val productsPerPage: Int,
    @SerializedName("TextSearch")
    val textSearch: String,
    @SerializedName("MicroDesigners")
    val microDesigners: List<Any>,
    @SerializedName("Attributes")
    val attributes: Attributes
)