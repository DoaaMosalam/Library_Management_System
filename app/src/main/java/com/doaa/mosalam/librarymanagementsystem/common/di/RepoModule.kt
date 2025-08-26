package com.doaa.mosalam.librarymanagementsystem.common.di

import com.doaa.mosalam.data.remote.APIService
import com.doaa.mosalam.data.repository.BooksRepoImp
import com.doaa.mosalam.data.repository.CategoriesRepoImp
import com.doaa.mosalam.data.repository.SearchRepoImp
import com.doaa.mosalam.domain.repo.BooksRepo
import com.doaa.mosalam.domain.repo.CategoriesRepo
import com.doaa.mosalam.domain.repo.SearchRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideBooksRepo(
        apiService: APIService
    ): BooksRepo {
        return BooksRepoImp(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryRepo(
        apiService: APIService
    ): CategoriesRepo {
        return CategoriesRepoImp(apiService)
    }

    @Provides
    @Singleton
    fun provideSearchProductRepo(
        apiService: APIService
    ): SearchRepo {
        return SearchRepoImp(apiService)
    }


}