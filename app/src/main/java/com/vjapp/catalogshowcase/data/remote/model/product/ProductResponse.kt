package com.vjapp.catalogshowcase.data.remote.model.product


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("ImageUrls")
    val imageUrls: ImageUrls,

    @SerializedName("ItemDescriptions")
    val itemDescriptions: ItemDescriptions, //*

    @SerializedName("Cod10")
    val cod10: String,

    @SerializedName("Department")
    val department: String,

    @SerializedName("Quantity")
    val quantity: Int,

    @SerializedName("Brand") //*
    val brand: Brand,

    @SerializedName("BrandId")
    val brandId: Int,

    @SerializedName("Category") //*
    val category: Category,

    @SerializedName("Price") //*
    val price: Price,

    @SerializedName("FormattedPrice") //*
    val formattedPrice: FormattedPrice,

    @SerializedName("MacroCategory")
    val macroCategory: String,

    @SerializedName("MicroCategoryPlural")
    val microCategoryPlural: String,

    @SerializedName("HasInfoPrice")
    val hasInfoPrice: Boolean,

    @SerializedName("HasCustomInfoPriceLayer")
    val hasCustomInfoPriceLayer: Boolean,

    @SerializedName("Colors")
    val colors: List<Color>, //*

    @SerializedName("Sizes")
    val sizes: List<Size>, //*

    @SerializedName("ErrorCode")
    val errorCode: Int
)