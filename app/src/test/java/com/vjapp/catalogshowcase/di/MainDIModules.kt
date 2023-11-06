package com.vjapp.catalogshowcase.di

import com.vjapp.catalogshowcase.cdi.domainModule
import com.vjapp.catalogshowcase.cdi.repositoryModule
import localModule

/**
 * Main Koin DI component.
 * Helps to configure
 * 1) Mockwebserver
 * 2) Usecase
 * 3) Repository
 */
fun configureTestAppComponent(baseApi: String)
        = listOf(
    MockWebServerDIPTest,
    configureNetworkRemoteModuleForTest(baseApi),
    viewModelModuleForTesting,
    repositoryModule,
    localModule,
    domainModule
    )

