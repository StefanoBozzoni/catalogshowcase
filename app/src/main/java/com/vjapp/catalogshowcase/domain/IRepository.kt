package com.vjapp.catalogshowcase.domain

import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.ProductEntity

interface IRepository {
    suspend fun httpBinGetDemo(): String
    suspend fun getCatalog(orderType:String): CatalogEntity
    suspend fun getProduct(): ProductEntity
}