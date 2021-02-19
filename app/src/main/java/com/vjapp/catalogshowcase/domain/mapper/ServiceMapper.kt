package com.vjapp.catalogshowcase.domain.mapper

import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogItemResponse
import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogResponse
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.CatalogItemEntity
import com.vjapp.catalogshowcase.domain.model.SearchTypes

class ServiceMapper {

    companion object {
        fun mapToEntity(catalogResponse: CatalogResponse): CatalogEntity {
            return CatalogEntity(catalogResponse.items.map { el -> mapToEntity(el) })
        }

        fun mapToEntity(catalogItemResponse: CatalogItemResponse): CatalogItemEntity {
            return CatalogItemEntity(
                brandName = catalogItemResponse.brand,
                category = catalogItemResponse.microCategory,
                price = if (catalogItemResponse.formattedDiscountedPrice == catalogItemResponse.formattedFullPrice)
                    catalogItemResponse.formattedFullPrice
                else catalogItemResponse.formattedDiscountedPrice,
                cod10 = catalogItemResponse.cod10
            )
        }

        fun mapOrderType(orderType: SearchTypes): String {
            return when (orderType) {
                SearchTypes.SEARCHRESULT -> "searchresult"
                SearchTypes.LATEST -> "latest"
                SearchTypes.LOWEST -> "lowest"
                SearchTypes.HIGHEST -> "highest"
            }
        }
    }

}