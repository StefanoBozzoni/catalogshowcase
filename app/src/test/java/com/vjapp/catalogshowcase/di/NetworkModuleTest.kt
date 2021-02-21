package com.vjapp.catalogshowcase.di

import com.vjapp.catalogshowcase.data.repository.datasource.RemoteDataSource
import com.vjapp.catalogshowcase.data.setup.AppServiceFactory
import com.vjapp.catalogshowcase.data.setup.HttpClientFactory
import com.vjapp.catalogshowcase.data.setup.ServiceFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun configureNetworkRemoteModuleForTest(baseApi: String) =
    /*
    module{
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    factory{ get<Retrofit>().create(AppService::class.java) }
   */


    module {
        single(named("HTTP_CLIENT")) { HttpClientFactory("", emptyList()) }
        single(named("SERVICE_FACTORY")) { ServiceFactory(baseApi) }

        //single { (get("SERVICE_FACTORY") as ServiceFactory).retrofitInstance }
        single(named("APP_SERVICE")) {
                AppServiceFactory(get(named("HTTP_CLIENT"))).getAppService(get(named("SERVICE_FACTORY")))
        }
        single { RemoteDataSource(get(named("APP_SERVICE"))) as RemoteDataSource }

    }

