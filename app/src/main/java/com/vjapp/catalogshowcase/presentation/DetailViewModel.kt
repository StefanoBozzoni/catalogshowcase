package com.vjapp.catalogshowcase.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetProductUseCase
import com.vjapp.catalogshowcase.domain.model.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailViewModel(private val getProductUseCase: GetProductUseCase) : ViewModel() {
    val getProductLiveData = MutableLiveData<Resource<ProductEntity>>()

    fun getProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getProductLiveData.postValue(Resource.loading())
                val product = async { getProductUseCase.execute() }.await()
                getProductLiveData.postValue(Resource.success(product))
            } catch (t: Throwable) {
                getProductLiveData.postValue(Resource.error("Errore caricamento Prodotto"))
            }
        }
    }

}
