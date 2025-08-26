package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.remote.APIService
import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse
import com.doaa.mosalam.domain.repo.CategoriesRepo
import javax.inject.Inject

class CategoriesRepoImp @Inject constructor(
    private val apiService: APIService
): CategoriesRepo {
    override suspend fun getBooksByCategory(category: String): BooksResponse {
        return apiService.getBooksByCategory(category)
    }

}