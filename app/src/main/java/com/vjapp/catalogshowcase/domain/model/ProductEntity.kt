package com.vjapp.catalogshowcase.domain.model


data class ProductEntity(

    val itemDescriptions: ItemDescriptionsEntity,

    val cod10: String,

    val quantity: Int,

    val brandName: String,

    val category: String,

    val price: String,

    val colors: List<ColorEntity>,

    val sizes: List<SizeEntity>

)