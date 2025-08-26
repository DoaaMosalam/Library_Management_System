package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse


interface SearchRepo {
    suspend fun searchBooks(query:String) : BooksResponse


}