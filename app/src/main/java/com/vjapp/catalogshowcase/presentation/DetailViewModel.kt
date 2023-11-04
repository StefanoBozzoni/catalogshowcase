package com.vjapp.catalogshowcase.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(private val getProductUseCase: GetProductUseCase,
                      private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {
    val getProductLiveData = MutableLiveData<Resource<ProductEntity>>()

    fun getProductLiveDataState():LiveData<Resource<ProductEntity>> = getProductLiveData

    fun getProduct() {
        viewModelScope.launch {
            try {
                System.out.println("dispatcher name: ${coroutineDispatcher}")
                EspressoIdlingResource.increment()
                getProductLiveData.postValue(Resource.loading())
                System.out.println("invoco getProduct: ${coroutineDispatcher.toString()}")
                val product = getProductUseCase.execute()
                System.out.println("fine invocazione getProduct: ${coroutineDispatcher.toString()}")
                getProductLiveData.postValue(Resource.success(product))
                EspressoIdlingResource.decrement()
            } catch (t: Throwable) {
                System.out.println("---->dispatcher name: ${coroutineDispatcher.toString()}")
                System.out.println("---->errore: ${t.message}")
                getProductLiveData.postValue(Resource.error("Errore caricamento Prodotto ${t.message}"))
                EspressoIdlingResource.decrement()
            }
        }
    }

}
