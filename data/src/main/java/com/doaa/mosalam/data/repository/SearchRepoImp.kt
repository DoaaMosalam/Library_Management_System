package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.remote.APIService
import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse

import com.doaa.mosalam.domain.repo.SearchRepo
import javax.inject.Inject

class SearchRepoImp @Inject constructor(
    private val apiService: APIService
) : SearchRepo {

    override suspend fun searchBooks(query: String): BooksResponse {
        return apiService.searchBooks(query)
    }
}