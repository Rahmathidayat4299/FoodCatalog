package com.course.modularfoodcatalog.viewmodels


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *hrahm,29/04/2024, 10:58
 **/
object ViewModelModule {
    val viewModelModule = module {
        viewModel { MainViewModel(get(), get()) }
    }
}