package com.vjapp.catalogshowcase.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

/**
 * Creates Mockwebserver instance for testing
 */
val MockWebServerUITest = module {

    factory {
        MockWebServer()
    }

}