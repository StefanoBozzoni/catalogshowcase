package com.vjapp.catalogshowcase.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import kotlinx.coroutines.launch
import kotlin.coroutines.ContinuationInterceptor

class DetailViewModel(private val getProductUseCase: GetProductUseCase) : ViewModel() {
    val getProductLiveData = MutableLiveData<Resource<ProductEntity>>()

    fun getProductLiveDataState():LiveData<Resource<ProductEntity>> = getProductLiveData

    fun getProduct() {
        viewModelScope.launch {
            val dispatcherName = this.coroutineContext[ContinuationInterceptor]?.toString()
            try {
                System.out.println("dispatcher name: ${dispatcherName}")
                EspressoIdlingResource.increment()
                getProductLiveData.postValue(Resource.loading())
                System.out.println("invoco getProduct: ${dispatcherName}")
                val product = getProductUseCase.execute()
                System.out.println("fine invocazione getProduct: ${dispatcherName}")
                getProductLiveData.postValue(Resource.success(product))
                EspressoIdlingResource.decrement()
            } catch (t: Throwable) {
                System.out.println("---->dispatcher name: ${dispatcherName}")
                System.out.println("---->errore: ${t.message}")
                getProductLiveData.postValue(Resource.error("Errore caricamento Prodotto ${t.message}"))
                EspressoIdlingResource.decrement()
            }
        }
    }

}
