package com.vjapp.catalogshowcase.data.remote.model.catalog


import com.google.gson.annotations.SerializedName

data class CatalogItemResponse(
    @SerializedName("Cod10")
    val cod10: String,
    @SerializedName("Brand")
    val brand: String,
    @SerializedName("BrandId")
    val brandId: Int,
    @SerializedName("Author")
    val author: String,
    @SerializedName("MicroCategoryCode")
    val microCategoryCode: String,
    @SerializedName("MicroCategory")
    val microCategory: String,
    @SerializedName("MicroCategoryPlural")
    val microCategoryPlural: String,
    @SerializedName("MacroCategoryCode")
    val macroCategoryCode: String,
    @SerializedName("MacroCategory")
    val macroCategory: String,
    @SerializedName("MacroCategoryPlural")
    val macroCategoryPlural: String,
    @SerializedName("FullPrice")
    val fullPrice: Int,
    @SerializedName("DiscountedPrice")
    val discountedPrice: Int,
    @SerializedName("IsSoldout")
    val isSoldout: Boolean,
    @SerializedName("Sizes")
    val sizes: List<Size>,
    @SerializedName("Colors")
    val colors: List<Color>,
    @SerializedName("HasFlipSide")
    val hasFlipSide: Boolean,
    @SerializedName("HasModelImage")
    val hasModelImage: Boolean,
    @SerializedName("SalesLineId")
    val salesLineId: String,
    @SerializedName("FormattedFullPrice")
    val formattedFullPrice: String,
    @SerializedName("FormattedDiscountedPrice")
    val formattedDiscountedPrice: String,
    @SerializedName("DiscountedPriceEur")
    val discountedPriceEur: Int,
    @SerializedName("HasAlternativeImage")
    val hasAlternativeImage: Boolean,
    @SerializedName("Title")
    val title: String
)