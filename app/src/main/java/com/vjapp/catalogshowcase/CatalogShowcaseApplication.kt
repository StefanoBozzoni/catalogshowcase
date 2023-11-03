package com.vjapp.catalogshowcase

import android.app.Application
import android.util.Log
import com.vjapp.catalogshowcase.cdi.domainModule
import com.vjapp.catalogshowcase.cdi.repositoryModule
import com.vjapp.catalogshowcase.cdi.viewModelModule
import localModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.core.module.Module
import remoteModule

open class CatalogShowcaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatalogShowcaseApplication)
            KoinLogger()
            modules(viewModelModule, repositoryModule, localModule, remoteModule, domainModule)
        }

    }

    class KoinLogger : Logger() {
        override fun display(level: Level, msg: MESSAGE) {
            Log.i("KOIN",msg)
        }
    }

    /*
    open fun getKoinModuleList():List<Module> {
        return listOf(viewModelModule, repositoryModule, localModule, remoteModule, domainModule)
    }

     */
}
