package com.vjapp.catalogshowcase.data.repository

import android.content.Context
import com.vjapp.catalogshowcase.data.repository.datasource.RemoteDataSource
import com.vjapp.catalogshowcase.domain.IRepository
import com.vjapp.catalogshowcase.domain.mapper.ServiceMapper
import com.vjapp.catalogshowcase.domain.model.CatalogEntity

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val context: Context
) : IRepository {

    override suspend fun httpBinGetDemo(): String {
        return remoteDataSource.httpBinDemo()
    }

    override suspend fun getCatalog(orderType : String): CatalogEntity {
        return ServiceMapper.mapToEntity(remoteDataSource.getCatalog(orderType))
    }

}