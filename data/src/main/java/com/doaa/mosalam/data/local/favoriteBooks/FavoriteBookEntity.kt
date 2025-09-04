package com.doaa.mosalam.data.local.favoriteBooks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val authors: String?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val categories: String?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val thumbnail: String?,
    //val readingStatus: String = "not_started"
    val readingStatus: String?
)


