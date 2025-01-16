package com.example.myapplication.di

import com.example.myapplication.viewmodel.FilmViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FilmViewModel(get()) }
}
