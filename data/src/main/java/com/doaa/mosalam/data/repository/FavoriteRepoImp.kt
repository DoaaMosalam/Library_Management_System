package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.local.favoriteBooks.FavoriteDao
import com.doaa.mosalam.data.mapper.toDomain
import com.doaa.mosalam.data.mapper.toEntity

import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import com.doaa.mosalam.domain.repo.FavoriteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepoImp @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepo{
    override suspend fun addBooksToFavorites(book: FavoriteBooks) {
        favoriteDao.addBooksToFavorites(book.toEntity())
    }

    override suspend fun removeFromFavorites(bookId: String) {
        favoriteDao.removeFromFavorites(bookId)
    }

    override fun getBooksFromFavorites(): Flow<List<FavoriteBooks>> {
        return favoriteDao.getBooksFromFavorites().map { list ->
            list.map { it.toDomain() }
        }

    }


    override fun isFavorite(bookId: String): Flow<Boolean> {
        return favoriteDao.getBooksFromFavorites().map { list ->
            list.any { it.id == bookId }
        }
    }

    override suspend fun updateReadingStatus(bookId: String, status: String) = favoriteDao.updateReadingStatus(bookId, status)


}