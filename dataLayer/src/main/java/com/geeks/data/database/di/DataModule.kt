package com.geeks.data.database.di

import androidx.room.Room
import com.example.domain.repository.TaskManagerRepository
import com.geeks.data.database.AppDatabase
import com.geeks.data.repositoryImpl.TaskManagerRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModules: Module = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "TaskDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().taskManagerDao() }


    single<TaskManagerRepository> { TaskManagerRepositoryImpl(get()) }
}