package com.vjapp.catalogshowcase.di

import com.vjapp.catalogshowcase.cdi.domainModule
import com.vjapp.catalogshowcase.cdi.repositoryModule
import com.vjapp.catalogshowcase.cdi.viewModelModule
import localModule
import remoteModule

/**
 * Main Koin DI component.
 * Helps to configure
 * 1) Mockwebserver
 * 2) Usecase
 * 3) Repository
 */
fun configureEspressoTestAppComponent(baseApi: String) =
    listOf(
    MockWebServerUITest,
    configureNetworkRemoteModuleForInstrumentationTest(baseApi),
    viewModelModule,
    repositoryModule,
    localModule,
    domainModule
    )



