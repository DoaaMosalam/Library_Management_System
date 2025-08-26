package com.doaa.mosalam.librarymanagementsystem.common.di

import com.doaa.mosalam.domain.repo.BooksRepo
import com.doaa.mosalam.domain.repo.CategoriesRepo
import com.doaa.mosalam.domain.repo.SearchRepo
import com.doaa.mosalam.domain.useCase.BooksUseCase
import com.doaa.mosalam.domain.useCase.CategoriesUseCase
import com.doaa.mosalam.domain.useCase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModel {
    @Provides
    @Singleton
    fun provideBooksUSeCase(booksRepo: BooksRepo): BooksUseCase {
        return BooksUseCase(booksRepo)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCase(categoriesRepo: CategoriesRepo): CategoriesUseCase {
        return CategoriesUseCase(categoriesRepo)
    }

    @Provides
    @Singleton
    fun provideSearchUseCase(searchRepo: SearchRepo): SearchUseCase {
        return SearchUseCase(searchRepo)
    }
}