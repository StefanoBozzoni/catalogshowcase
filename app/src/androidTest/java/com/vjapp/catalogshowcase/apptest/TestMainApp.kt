package com.vjapp.catalogshowcase.apptest

import com.vjapp.catalogshowcase.CatalogShowcaseApplication
import org.koin.core.module.Module

/**
 * Helps to configure required dependencies for Instru Tests.
 * Method provideDependency can be overrided and new dependencies can be supplied.
 */
class TestMainApp : CatalogShowcaseApplication() {
    //override fun getKoinModuleList(): List<Module> {
    //    return emptyList()
    //}
}