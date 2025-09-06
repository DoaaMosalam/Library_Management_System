package com.doaa.mosalam.librarymanagementsystem.common.di

import com.doaa.mosalam.domain.repo.DetailsRepo
import com.doaa.mosalam.domain.repo.FavoriteRepo
import com.doaa.mosalam.domain.repo.LoginRepo
import com.doaa.mosalam.domain.repo.RegisterRepo
import com.doaa.mosalam.domain.useCase.DetailsUseCase
import com.doaa.mosalam.domain.useCase.FavoriteUseCase
import com.doaa.mosalam.domain.useCase.LoginUseCase
import com.doaa.mosalam.domain.useCase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseRoomDatabaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepo: LoginRepo):
            LoginUseCase {
        return LoginUseCase(loginRepo)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(registerRepo: RegisterRepo):
            RegisterUseCase {
        return RegisterUseCase(registerRepo)
    }

    @Provides
    @Singleton
    fun provideFavoriteUseCase(favoriteRepo: FavoriteRepo):
            FavoriteUseCase {
        return FavoriteUseCase(favoriteRepo)
    }

    @Provides
    @Singleton
    fun provideDetailsUseCase(detailsRepo: DetailsRepo):
            DetailsUseCase {
        return DetailsUseCase(detailsRepo)

    }
}