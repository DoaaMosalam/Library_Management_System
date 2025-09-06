package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.trendingBooks.Volume

interface DetailsRepo {
    suspend fun getBookDetails(bookId: String): Volume
}