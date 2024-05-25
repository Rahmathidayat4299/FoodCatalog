package com.course.core.di


import com.course.core.data.FoodRepository
import com.course.core.data.FoodRepositoryImpl
import com.course.core.data.domain.FoodInteractor
import com.course.core.data.domain.FoodUseCase
import com.course.core.data.local.AppDatabase
import com.course.core.data.local.datasource.LocalDataSource
import com.course.core.data.local.datasource.LocalDataSourceImpl
import com.course.core.data.remote.ApiService
import com.course.core.data.remote.RemoteDataSource
import com.course.core.data.remote.RemoteDataSourceImpl
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
        single<LocalDataSource> { LocalDataSourceImpl(get()) }
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().recipesDao() }
    }
    val remoteDataSourceModule = module {
        single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    }


    val repositoryModule = module {
        single<FoodRepository> { FoodRepositoryImpl(get(), get()) }
        single<FoodUseCase> { FoodInteractor(get()) }
    }

    val module: List<Module> = listOf(
        networkModules, localModule, remoteDataSourceModule, repositoryModule
    )

}