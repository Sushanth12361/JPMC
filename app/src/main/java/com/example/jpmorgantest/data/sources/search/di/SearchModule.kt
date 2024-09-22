package com.example.jpmorgantest.data.sources.search.di

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.data.sources.search.SearchRepository
import com.example.jpmorgantest.data.sources.search.local.SearchLocalDataSource
import com.example.jpmorgantest.data.sources.search.local.SearchLocalDataSourceImpl
import com.example.jpmorgantest.data.sources.search.mapper.SearchResultMapper
import com.example.jpmorgantest.data.sources.search.mapper.SearchResultMapperImpl
import com.example.jpmorgantest.data.sources.search.remote.SearchRemoteDataSource
import com.example.jpmorgantest.data.sources.search.remote.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Singleton
    @Binds
    abstract fun bindsSearchDataSource(searchRepository: SearchRepository): SearchDataSource

    @Singleton
    @Binds
    abstract fun bindsSearchResultMapper(
        searchResultMapper: SearchResultMapperImpl
    ): SearchResultMapper

    @Singleton
    @Binds
    abstract fun bindsSearchRemoteDataSource(
        searchResultRemoteDataSource: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsSearchLocalDataSource(
        searchLocalDataSource: SearchLocalDataSourceImpl
    ): SearchLocalDataSource
}
