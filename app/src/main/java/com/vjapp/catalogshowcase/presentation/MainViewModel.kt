package com.vjapp.catalogshowcase.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.OperationType
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import kotlinx.coroutines.*

class MainViewModel(private val getCatalogUseCase: GetCatalogUseCase, private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {
    val getCatalogLiveData = MutableLiveData<Pair<Resource<CatalogEntity>, OperationType>>()

    fun getCatalog(searchType: SearchTypes, operationType: OperationType =OperationType.REPLACE_LIST) {
        viewModelScope.launch(coroutineDispatcher) {
            try {
                getCatalogLiveData.postValue(Pair(Resource.loading(), operationType))
                coroutineScope {
                    val catalog =
                        async { getCatalogUseCase.execute(GetCatalogUseCase.Params(searchType)) }.await()
                    getCatalogLiveData.postValue(Pair(Resource.success(catalog), operationType))
                }
            } catch (t: Throwable) {
                getCatalogLiveData.postValue(Pair(Resource.error("Errore caricamento catalogo"), operationType))
            }
        }
    }

}
