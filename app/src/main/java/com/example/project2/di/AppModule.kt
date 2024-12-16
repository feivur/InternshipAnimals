package com.example.project2.di

import com.example.project2.server.ApiService
import com.example.project2.server.RetrofitInstance
import com.example.project2.utils.AnimalsRepository
import com.example.project2.utils.ServerRepository
import com.example.project2.utils.animalsStub.StubAnimalsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideServerRepository(apiService: ApiService): ServerRepository {
        return ServerRepository(apiService)
    }


    @Provides
    @Singleton
    fun provideAnimalsRepository(): AnimalsRepository {
        return StubAnimalsRepository() // Заглушка
    }
}