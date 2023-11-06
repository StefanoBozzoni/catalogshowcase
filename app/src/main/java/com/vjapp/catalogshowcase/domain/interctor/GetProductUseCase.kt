package com.vjapp.catalogshowcase.domain.interctor

import com.vjapp.catalogshowcase.domain.IRepository
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProductUseCase(private val remoteRepository: IRepository) {
    suspend fun execute(): ProductEntity {
        return withContext(Dispatchers.IO)  {
            remoteRepository.getProduct()
        }
    }
}