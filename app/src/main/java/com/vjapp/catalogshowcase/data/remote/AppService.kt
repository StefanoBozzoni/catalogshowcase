package com.vjapp.catalogshowcase.data.remote

import com.vjapp.catalogshowcase.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface AppService {

    //Demo api, just to see if retrofit is working
    @GET("https://httpbin.org/get")
    suspend fun httpBinGetDemo(): Response<Resphttpbin>

}