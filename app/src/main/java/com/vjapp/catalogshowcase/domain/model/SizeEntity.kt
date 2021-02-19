package com.vjapp.catalogshowcase.domain.model


data class SizeEntity(
    val id: Int,
    val name: String,
    val isoTwoLetterCountryCode: String,
    val defaultSizeLabel: String,
    val alternativeSizeLabel: String
)