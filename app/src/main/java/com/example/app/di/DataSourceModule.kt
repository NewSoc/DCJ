package com.example.app.di

import com.example.data.repository.datasource.MainDataSource
import com.example.data.repository.datasourceimpl.MainDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun provideMainDataSource(
        firestore:FirebaseFirestore
    ) : MainDataSource {
        return MainDataSourceImpl(firestore)
    }
}