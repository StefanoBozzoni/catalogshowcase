package com.vjapp.catalogshowcase.di

import com.vjapp.catalogshowcase.presentation.DetailViewModel
import com.vjapp.catalogshowcase.presentation.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val viewModelModuleForTesting = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
