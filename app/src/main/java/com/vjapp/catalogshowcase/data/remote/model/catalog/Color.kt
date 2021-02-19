package com.vjapp.catalogshowcase.data.remote.model.catalog


import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Cod10")
    val cod10: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Rgb")
    val rgb: String
)