package com.terranullius.bhoomicabs.di

import com.terranullius.bhoomicabs.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Singleton
    @Provides
    fun providesMainRepository(): MainRepository = MainRepository()
}