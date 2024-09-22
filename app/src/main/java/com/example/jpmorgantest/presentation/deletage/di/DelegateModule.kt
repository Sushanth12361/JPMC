package com.example.jpmorgantest.presentation.deletage.di

import com.example.jpmorgantest.presentation.deletage.LocationDelegate
import com.example.jpmorgantest.presentation.deletage.LocationDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DelegateModule {
    @Singleton
    @Binds
    abstract fun bindLocationDelegate(
        locationDelegateImpl: LocationDelegateImpl
    ): LocationDelegate
}
