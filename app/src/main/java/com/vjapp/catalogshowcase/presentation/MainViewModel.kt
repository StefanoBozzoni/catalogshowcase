package com.vjapp.catalogshowcase.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjapp.catalogshowcase.domain.interctor.GetCatalogUseCase
import com.vjapp.catalogshowcase.domain.model.CatalogEntity
import com.vjapp.catalogshowcase.domain.model.OperationType
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainViewModel(private val getCatalogUseCase: GetCatalogUseCase, private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {
    val getCatalogLiveData = MutableLiveData<Pair<Resource<CatalogEntity>, OperationType>>()

    fun getCatalog(searchType: SearchTypes, operationType: OperationType =OperationType.REPLACE_LIST) {
        viewModelScope.launch(coroutineDispatcher) {
            try {
                EspressoIdlingResource.increment()
                getCatalogLiveData.postValue(Pair(Resource.loading(), operationType))
                coroutineScope {
                    val catalog =
                        async { getCatalogUseCase.execute(GetCatalogUseCase.Params(searchType)) }.await()
                    getCatalogLiveData.postValue(Pair(Resource.success(catalog), operationType))
                    EspressoIdlingResource.decrement()
                }
            } catch (t: Throwable) {
                getCatalogLiveData.postValue(Pair(Resource.error("Errore caricamento catalogo"), operationType))
                EspressoIdlingResource.decrement()
            }
        }
    }

}
