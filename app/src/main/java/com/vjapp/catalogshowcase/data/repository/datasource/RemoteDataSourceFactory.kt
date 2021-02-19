package com.vjapp.catalogshowcase.data.repository.datasource

class RemoteDataSourceFactory(private val remoteDataSource: RemoteDataSource) {
    fun retrieveRemoteDataSource() = remoteDataSource
}