package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import com.doaa.mosalam.domain.repo.FavoriteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val favoriteRepo: FavoriteRepo
) {
    suspend fun addBooksToFavorites(books: FavoriteBooks)= favoriteRepo.addBooksToFavorites(books)
    suspend fun removeFromFavorites(bookId: String)= favoriteRepo.removeFromFavorites(bookId)

    fun getBooksFromFavorites()= favoriteRepo.getBooksFromFavorites()
    fun isFavorite(bookId: String): Flow<Boolean> {
        return favoriteRepo.getBooksFromFavorites().map { list ->
            list.any { it.id == bookId }
        }
    }

    suspend fun updateReadingStatus(bookId: String, status: String) = favoriteRepo.updateReadingStatus(bookId, status)

}