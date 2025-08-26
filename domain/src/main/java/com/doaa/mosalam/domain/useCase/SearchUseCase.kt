package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse
import com.doaa.mosalam.domain.repo.SearchRepo
import kotlinx.coroutines.flow.Flow

class SearchUseCase(private val searchRepo: SearchRepo) {

    suspend fun searchBooks(query:String) : BooksResponse = searchRepo.searchBooks(
        query
    )
}