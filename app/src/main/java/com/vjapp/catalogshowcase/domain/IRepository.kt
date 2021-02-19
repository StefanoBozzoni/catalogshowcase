package com.vjapp.catalogshowcase.domain

import com.vjapp.catalogshowcase.domain.model.CatalogEntity

interface IRepository {
    suspend fun httpBinGetDemo(): String
    suspend fun getCatalog(orderType:String): CatalogEntity

}