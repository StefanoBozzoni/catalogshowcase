package com.vjapp.catalogshowcase.domain.model

data class CatalogEntity(
    val catalogList: List<CatalogItemEntity>
)

data class CatalogItemEntity(
    val brandName: String,
    val category: String,
    val price: String,
    val cod10:String
)