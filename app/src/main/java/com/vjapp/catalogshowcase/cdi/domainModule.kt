package com.vjapp.catalogshowcase.cdi

import com.vjapp.catalogshowcase.domain.IRepository
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import org.koin.dsl.module


val domainModule = module {
    factory { GetCatalogUseCase(get<IRepository>()) }
    factory { GetProductUseCase(get()) }
}
