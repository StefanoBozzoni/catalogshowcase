package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class Size(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("IsoTwoLetterCountryCode")
    val isoTwoLetterCountryCode: String,
    @SerializedName("DefaultSizeLabel")
    val defaultSizeLabel: String,
    @SerializedName("AlternativeSizeLabel")
    val alternativeSizeLabel: String
)