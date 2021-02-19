package com.vjapp.catalogshowcase.data.remote.model.catalog
import com.google.gson.annotations.SerializedName

data class CatalogResponse(
    @SerializedName("SearchResultTitle")
    val searchResultTitle: String,
    @SerializedName("Items")
    val items: List<CatalogItemResponse>,
    @SerializedName("SearchRequest")
    val searchRequest: SearchRequest,
    @SerializedName("Area")
    val area: String,
    @SerializedName("Analytics")
    val analytics: Analytics,
    @SerializedName("BrandBannerActionUrl")
    val brandBannerActionUrl: String,
    @SerializedName("MobileFriendlyUrl")
    val mobileFriendlyUrl: String,
    @SerializedName("Department")
    val department: String,
    @SerializedName("ErrorCode")
    val errorCode: Int
)