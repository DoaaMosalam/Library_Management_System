package com.doaa.mosalam.librarymanagementsystem.common.di

import android.app.Application
import android.content.Context
import com.holeCode.novamoda.data.repository.auth.FirebaseAuthRepository
import com.holeCode.novamoda.data.repository.auth.FirebaseAuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // firebase auth repository provider can be added here in the future

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(): FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl() // Assuming FirebaseAuthRepositoryImpl is your implementation
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}