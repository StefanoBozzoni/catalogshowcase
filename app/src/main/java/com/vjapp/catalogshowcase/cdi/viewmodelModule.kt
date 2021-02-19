package com.vjapp.catalogshowcase.cdi

import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
