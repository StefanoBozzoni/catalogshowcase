package com.vjapp.catalogshowcase.data.repository.datasource

import com.vjapp.catalogshowcase.data.exceptions.NetworkCommunicationException
import com.vjapp.catalogshowcase.data.remote.AppService
import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogResponse

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


}