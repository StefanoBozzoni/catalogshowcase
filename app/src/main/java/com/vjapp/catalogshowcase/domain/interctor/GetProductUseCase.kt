package com.vjapp.catalogshowcase.domain.interctor

import com.vjapp.catalogshowcase.domain.IRepository
import com.vjapp.catalogshowcase.domain.model.ProductEntity

class GetProductUseCase(private val remoteRepository: IRepository) {
    suspend fun execute(): ProductEntity {
        return remoteRepository.getProduct()
    }
}