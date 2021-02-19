package com.vjapp.catalogshowcase.data.repository

import android.content.Context
import com.vjapp.catalogshowcase.data.repository.datasource.RemoteDataSource
import com.vjapp.catalogshowcase.domain.IRepository

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val context: Context
) : IRepository {

    override suspend fun httpBinGetDemo(): String {
        return remoteDataSource.httpBinDemo()
    }

}