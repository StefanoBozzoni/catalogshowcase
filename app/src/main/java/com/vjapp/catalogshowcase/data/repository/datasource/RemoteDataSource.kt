package com.vjapp.catalogshowcase.data.repository.datasource

import com.vjapp.catalogshowcase.data.exceptions.NetworkCommunicationException
import com.vjapp.catalogshowcase.data.remote.AppService
import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogResponse
import com.vjapp.catalogshowcase.data.remote.model.product.ProductResponse

class RemoteDataSource(
    private val appService: AppService
) {

    suspend fun httpBinDemo(): String {
        val result = appService.httpBinGetDemo()
        return result.body().toString()
    }

    suspend fun getCatalog(orderType: String): CatalogResponse {
        val response = appService.getCatalogList(orderType)
        if (response.isSuccessful) {
            response.body()?.let { return it }
        }
        throw NetworkCommunicationException()
    }

    suspend fun getProduct(): ProductResponse {
        val response = appService.getProduct()

        if (response.isSuccessful) {
            response.body()?.let { return it }
        }

        throw NetworkCommunicationException()
    }


}