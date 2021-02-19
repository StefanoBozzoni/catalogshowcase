package com.vjapp.catalogshowcase.data.setup

import com.vjapp.catalogshowcase.data.remote.AppService

class AppServiceFactory(private val httpClientFactory: HttpClientFactory) {

    fun getAppService(serviceFactory : ServiceFactory): AppService {
        val ariaHttpClient = httpClientFactory.abstractClient.newBuilder()
            //.addInterceptor(NetworkModule.createHeadersInterceptor())
            .build()
        val appService = serviceFactory.retrofitInstance.newBuilder().client(ariaHttpClient).build()
        return appService.create(AppService::class.java)
    }

}

