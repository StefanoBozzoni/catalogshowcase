package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("ColorId")
    val colorId: Int,
    @SerializedName("ColorCode")
    val colorCode: String,
    @SerializedName("Code10")
    val code10: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Rgb")
    val rgb: String
)