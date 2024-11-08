package com.geeks.cleanArch.presentation.di

import com.geeks.cleanArch.presentation.activity.MainActivityViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val uiModule: Module = module {

    factory { MainActivityViewModel(get(), get()) }
}