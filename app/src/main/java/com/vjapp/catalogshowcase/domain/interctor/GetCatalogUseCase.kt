package com.vjapp.catalogshowcase.domain.interctor

import com.vjapp.catalogshowcase.domain.IRepository
import com.vjapp.catalogshowcase.domain.mapper.ServiceMapper
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCatalogUseCase(private val remoteRepository: IRepository) {
    suspend fun execute(params:Params): CatalogEntity  {
          return remoteRepository.getCatalog(ServiceMapper.mapOrderType(params.orderType))
    }

    class Params(val orderType: SearchTypes)

}