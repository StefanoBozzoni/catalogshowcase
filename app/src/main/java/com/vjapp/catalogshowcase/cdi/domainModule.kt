package com.vjapp.catalogshowcase.cdi

import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import org.koin.dsl.module.module

val domainModule = module {
    factory { GetCatalogUseCase(get()) }
    factory { GetProductUseCase(get()) }
}
