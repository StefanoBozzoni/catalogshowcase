package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Url")
    val url: String,
    @SerializedName("Rel")
    val rel: String
)