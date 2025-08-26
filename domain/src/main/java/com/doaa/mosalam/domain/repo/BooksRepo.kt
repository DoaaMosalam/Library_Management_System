package com.doaa.mosalam.domain.repo


import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse


interface BooksRepo {

    suspend fun  getTrendingBooks(): BooksResponse


}