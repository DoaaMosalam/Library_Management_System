package com.doaa.mosalam.data.local.favoriteBooks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(book: FavoriteBookEntity)

    @Query("DELETE FROM favorite_books WHERE id = :bookId")
    suspend fun removeFavorite(bookId: String)

    @Query("SELECT * FROM favorite_books")
    fun getAllFavorites(): Flow<List<FavoriteBookEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_books WHERE id = :bookId)")
    fun isFavorite(bookId: String): Flow<Boolean>
}
