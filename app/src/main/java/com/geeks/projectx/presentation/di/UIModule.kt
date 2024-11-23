package com.geeks.projectx.presentation.di

import com.geeks.projectx.presentation.fragments.TaskViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val uiModule: Module = module {

    factory { TaskViewModel(get(), get(), get(), get(), get()) }
}