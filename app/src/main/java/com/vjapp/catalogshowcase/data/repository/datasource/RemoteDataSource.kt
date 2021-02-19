package com.vjapp.catalogshowcase.data.repository.datasource

import com.vjapp.catalogshowcase.data.exceptions.NetworkCommunicationException
import com.vjapp.catalogshowcase.data.remote.AppService

class RemoteDataSource(
    private val appService: AppService
) {

    suspend fun httpBinDemo(): String {
        val result = appService.httpBinGetDemo()
        return result.body().toString()
    }

}