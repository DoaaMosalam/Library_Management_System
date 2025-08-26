package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse
import com.doaa.mosalam.domain.repo.BooksRepo
import javax.inject.Inject

class BooksUseCase @Inject constructor(
    private val booksRepo: BooksRepo
) {
    suspend fun getTrendingBooks(): BooksResponse = booksRepo.getTrendingBooks()

}