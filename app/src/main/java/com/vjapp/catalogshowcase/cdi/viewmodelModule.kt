package com.vjapp.catalogshowcase.cdi

import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), Dispatchers.IO) }
    viewModel { DetailViewModel(get(), Dispatchers.IO) }
}
