package com.course.modularfoodcatalog

import android.app.Application
import com.course.core.di.AppModule
import com.course.modularfoodcatalog.viewmodels.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *hrahm,29/04/2024, 10:35
 **/
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModule.module + ViewModelModule.viewModelModule)
        }
    }
}