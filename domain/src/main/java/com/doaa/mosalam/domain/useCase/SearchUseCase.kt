package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse

import com.doaa.mosalam.domain.repo.SearchRepo

import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepo: SearchRepo
) {

    suspend fun searchBooks(query:String) : BooksResponse {
        return searchRepo.searchBooks(query)
    }
}