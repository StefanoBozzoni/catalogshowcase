package com.vjapp.catalogshowcase.domain.interctor

import com.vjapp.catalogshowcase.domain.IRepository
import com.vjapp.catalogshowcase.domain.mapper.ServiceMapper
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.SearchTypes

class GetCatalogUseCase(private val remoteRepository: IRepository) {
    suspend fun execute(): CatalogEntity {
        return remoteRepository.getCatalog("searchresult")
    }

}