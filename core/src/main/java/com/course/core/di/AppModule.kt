package com.course.core.di


import com.course.core.data.Repository
import com.course.core.data.local.AppDatabase
import com.course.core.data.local.CartRepository
import com.course.core.data.local.datasource.LocalDataSource
import com.course.core.data.remote.ApiService
import com.course.core.data.remote.RemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 *hrahm,23/04/2024, 18:57
 **/
object AppModule {
    val networkModules = module {
        single { ApiService.invoke() }

    }
    val localModule = module {

        factory { LocalDataSource(get()) }
        factory { AppDatabase.getInstance(androidContext()) }
        factory { get<AppDatabase>().recipesDao() }
    }
    val remoteDataSourceModule = module {
        factory { RemoteDataSource(get()) }

    }


    val repositoryModule = module {
        factory { CartRepository(get()) }
        factory { Repository(get()) }
    }

    val module: List<Module> = listOf(
        remoteDataSourceModule, networkModules, repositoryModule, localModule
    )
}