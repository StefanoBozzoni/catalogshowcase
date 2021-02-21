package com.vjapp.catalogshowcase.cdi

import com.vjapp.catalogshowcase.data.repository.Repository
import com.vjapp.catalogshowcase.data.repository.datasource.RemoteDataSourceFactory
import com.vjapp.catalogshowcase.domain.IRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { RemoteDataSourceFactory(get()) }
    single { Repository(get()) as IRepository }
}