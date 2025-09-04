package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {
    suspend fun addBooksToFavorites(book: FavoriteBooks)
    suspend fun removeFromFavorites(bookId: String)
    fun getBooksFromFavorites(): Flow<List<FavoriteBooks>>
    fun isFavorite(bookId: String): Flow<Boolean>

    suspend fun updateReadingStatus(bookId: String, status: String)
}