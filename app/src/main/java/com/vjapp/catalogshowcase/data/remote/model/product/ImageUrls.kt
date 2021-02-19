package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class ImageUrls(
    @SerializedName("Normal")
    val normal: List<String>,
    @SerializedName("Zoom")
    val zoom: List<String>,
    @SerializedName("Soldout")
    val soldout: String
)