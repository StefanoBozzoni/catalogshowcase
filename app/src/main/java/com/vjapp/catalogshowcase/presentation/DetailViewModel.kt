package com.vjapp.catalogshowcase.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import kotlinx.coroutines.*

class DetailViewModel(private val getProductUseCase: GetProductUseCase,
                      private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {
    val getProductLiveData = MutableLiveData<Resource<ProductEntity>>()

    fun getProductLiveDataState():LiveData<Resource<ProductEntity>> = getProductLiveData

    fun getProduct() {
        viewModelScope.launch(coroutineDispatcher) {
            try {
                getProductLiveData.postValue(Resource.loading())
                val product = getProductUseCase.execute()
                getProductLiveData.postValue(Resource.success(product))
            } catch (t: Throwable) {
                getProductLiveData.postValue(Resource.error("Errore caricamento Prodotto"))
            }
        }
    }

}
