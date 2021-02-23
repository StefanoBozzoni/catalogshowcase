package com.vjapp.catalogshowcase.domain.mapper

import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogItemResponse
import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogResponse
import com.vjapp.catalogshowcase.data.remote.model.product.Color
import com.vjapp.catalogshowcase.data.remote.model.product.ItemDescriptions
import com.vjapp.catalogshowcase.data.remote.model.product.ProductResponse
import com.vjapp.catalogshowcase.data.remote.model.product.Size
import com.vjapp.catalogshowcase.domain.model.*

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

        fun mapToEntity(productResponse: ProductResponse) : ProductEntity {
            with (productResponse) {
                return ProductEntity(
                    mapToEntity(itemDescriptions),
                    cod10,
                    quantity,
                    brand.name,
                    category.name,
                    price = if (formattedPrice.discountedPrice == formattedPrice.fullPrice)
                        formattedPrice.fullPrice else formattedPrice.discountedPrice,
                    colors = colors.map{ color->mapToEntity(color)},
                    sizes = sizes.map{ size->mapToEntity(size)}
                )
            }
        }

        fun mapToEntity(itemDescriptions: ItemDescriptions) : ItemDescriptionsEntity {
            with (itemDescriptions) {
                return ItemDescriptionsEntity(
                    sizeInfo,
                    productInfo,
                    brandInfo
                )
            }
        }

        fun mapToEntity(color : Color) : ColorEntity {
            with (color) {
                return ColorEntity(
                    colorId,
                    colorCode,
                    code10,
                    name,
                    rgb
                )
            }
        }

        fun mapOrderType(orderType: SearchTypes): String {
            return when (orderType) {
                SearchTypes.SEARCHRESULT -> "searchresult"
                SearchTypes.LATEST -> "latest"
                SearchTypes.LOWEST -> "lowest"
                SearchTypes.HIGHEST -> "highest"
                SearchTypes.SEARCHRESULT2 -> "searchresult2"
                SearchTypes.SEARCHRESULT3 -> "searchresult3"
            }
        }

        fun mapToEntity(size : Size) : SizeEntity {
            with (size) {
                return SizeEntity(
                    id,
                    name,
                    isoTwoLetterCountryCode,
                    defaultSizeLabel,
                    alternativeSizeLabel
                )
            }
        }
    }

}