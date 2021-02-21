package com.vjapp.catalogshowcase.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import kotlinx.coroutines.*
import java.lang.RuntimeException

class MainViewModel(private val getCatalogUseCase: GetCatalogUseCase, private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {
    val getCatalogLiveData = MutableLiveData<Resource<CatalogEntity>>()

    fun getCatalog() {
        viewModelScope.launch(coroutineDispatcher) {
            try {
                withContext(Dispatchers.Main) {
                    getCatalogLiveData.value = Resource.loading()
                }
                val catalog = async { getCatalogUseCase.execute()}.await()
                withContext(Dispatchers.Main) {
                    getCatalogLiveData.value=Resource.success(catalog)
                }
            } catch (t: Throwable) {
                getCatalogLiveData.postValue(Resource.error("Errore caricamento catalogo"))
            }
        }
    }

}
