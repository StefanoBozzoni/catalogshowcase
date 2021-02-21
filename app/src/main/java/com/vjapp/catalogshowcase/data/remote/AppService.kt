package com.vjapp.catalogshowcase.data.remote

import com.vjapp.catalogshowcase.data.remote.model.*
import com.vjapp.catalogshowcase.data.remote.model.catalog.CatalogResponse
import com.vjapp.catalogshowcase.data.remote.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.*

interface AppService {

    //Demo api, just to see if retrofit is working
    @GET("https://httpbin.org/get")
    suspend fun httpBinGetDemo(): Response<Resphttpbin>

    @GET("v1/{order}")
    suspend fun getCatalogList(@Path("order") order:String="searchresult"): Response<CatalogResponse>

    @GET("v1/item")
    suspend fun getProduct(): Response<ProductResponse>
}