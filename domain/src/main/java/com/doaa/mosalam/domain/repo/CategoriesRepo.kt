package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse

interface CategoriesRepo {
    suspend fun getBooksByCategory(category: String): BooksResponse
}