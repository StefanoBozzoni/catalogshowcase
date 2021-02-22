package com.vjapp.catalogshowcase

import android.app.Application
import com.vjapp.catalogshowcase.cdi.domainModule
import com.vjapp.catalogshowcase.cdi.repositoryModule
import com.vjapp.catalogshowcase.cdi.viewModelModule
import localModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import remoteModule

open class CatalogShowcaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CatalogShowcaseApplication)
            modules(getKoinModuleList())
        }

    }

    open fun getKoinModuleList():List<Module> {
        return listOf(viewModelModule, repositoryModule, localModule, remoteModule, domainModule)
    }
}
