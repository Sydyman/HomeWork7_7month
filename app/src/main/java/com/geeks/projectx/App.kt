package com.geeks.projectx

import android.app.Application
import com.example.addtaskfeature.module.addTaskModule
import com.example.domain.di.domainModule
import com.geeks.projectx.presentation.di.uiModule
import com.geeks.data.database.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(dataModules)
            modules(domainModule)
            modules(uiModule)
            modules(addTaskModule)
        }
    }
}