package com.doaa.mosalam.librarymanagementsystem.common.di

import com.doaa.mosalam.data.local.favoriteBooks.FavoriteDao
import com.doaa.mosalam.data.local.login.LoginDAO
import com.doaa.mosalam.data.local.register.RegisterDAO
import com.doaa.mosalam.data.repository.FavoriteRepoImp
import com.doaa.mosalam.data.repository.LoginRepoImp
import com.doaa.mosalam.data.repository.ProfileRepoImp
import com.doaa.mosalam.data.repository.RegisterRepoImp
import com.doaa.mosalam.domain.repo.FavoriteRepo
import com.doaa.mosalam.domain.repo.LoginRepo
import com.doaa.mosalam.domain.repo.ProfileRepo
import com.doaa.mosalam.domain.repo.RegisterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//    fun provideLoginRepo(registerDAO: RegisterDAO):
//            LoginRepo = LoginRepoImp(registerDAO)
@Module
@InstallIn(SingletonComponent::class)
object RepoRoomDataModule {

    @Provides
    @Singleton
    fun provideLoginRepo(registerDAO: RegisterDAO, loginDAO: LoginDAO):
            LoginRepo = LoginRepoImp(registerDAO, loginDAO)


    @Provides
    @Singleton
    fun provideRegisterRepo(registerDao: RegisterDAO):
            RegisterRepo = RegisterRepoImp(registerDao)

    @Provides
    @Singleton
    fun providesFavoriteRepo(favoriteDao: FavoriteDao):
            FavoriteRepo = FavoriteRepoImp(favoriteDao)

    @Provides
    @Singleton
    fun providesProfileRepo(registerDAO: RegisterDAO):
            ProfileRepo = ProfileRepoImp(registerDAO)

}