package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.local.favoriteBooks.FavoriteDao
import com.doaa.mosalam.data.mapper.mapToFavoriteDB
import com.doaa.mosalam.data.mapper.toDomain

import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import com.doaa.mosalam.domain.repo.FavoriteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepoImp @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepo{
    override suspend fun addToFavorites(book: FavoriteBooks) {
        favoriteDao.insertFavorite(book.mapToFavoriteDB())
    }

    override suspend fun removeFromFavorites(bookId: String) {
        favoriteDao.removeFavorite(bookId)
    }

    override fun getFavorites(): Flow<List<FavoriteBooks>> {
        return favoriteDao.getAllFavorites().map { favoriteEntities ->
            favoriteEntities.map { it.toDomain() }
        }
    }

    override fun isFavorite(bookId: String): Flow<Boolean> {
        return favoriteDao.isFavorite(bookId)
    }

}