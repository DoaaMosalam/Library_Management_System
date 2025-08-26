package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import com.doaa.mosalam.domain.repo.FavoriteRepo
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val favoriteRepo: FavoriteRepo
) {
    suspend fun addToFavorites(books: FavoriteBooks)= favoriteRepo.addToFavorites(books)
    suspend fun removeFromFavorites(bookId: String)= favoriteRepo.removeFromFavorites(bookId)
    fun getFavorites()= favoriteRepo.getFavorites()
    fun isFavorite(bookId: String)= favoriteRepo.isFavorite(bookId)
}