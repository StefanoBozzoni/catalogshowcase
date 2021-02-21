package com.vjapp.catalogshowcase

import android.app.Application
import com.vjapp.catalogshowcase.cdi.domainModule
import com.vjapp.catalogshowcase.cdi.repositoryModule
import com.vjapp.catalogshowcase.cdi.viewModelModule
import localModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import remoteModule

class CatalogShowcaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //startKoin(this,listOf(viewModelModule, repositoryModule,
        //                                   localModule, remoteModule, domainModule), logger= KoinLogger())

        startKoin {
            androidLogger()
            androidContext(this@CatalogShowcaseApplication)
            modules(listOf(viewModelModule, repositoryModule, localModule, remoteModule, domainModule)) }
    }
}
