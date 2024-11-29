package com.example.domain.di

import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllTasksUseCase
import com.example.domain.usecase.GetTaskUseCase
import com.example.domain.usecase.InsertTaskUseCase
import com.example.domain.usecase.UpdateTaskUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module = module {

    single { InsertTaskUseCase(get()) }
    single { GetAllTasksUseCase(get()) }
    single { UpdateTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetTaskUseCase(get()) }
}