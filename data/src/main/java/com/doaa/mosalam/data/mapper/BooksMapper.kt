package com.doaa.mosalam.data.mapper

import com.doaa.mosalam.data.local.favoriteBooks.FavoriteBookEntity
import com.doaa.mosalam.domain.model.favorite.FavoriteBooks


// Mapping Function
fun FavoriteBooks.toEntity(): FavoriteBookEntity {
    return FavoriteBookEntity(
        id = id ?: "",
        title = title,
        authors = authors,
        description = description,
        pageCount = pageCount,
        categories = categories,
        thumbnail = thumbnail,
        publisher = publisher,
        publishedDate = publishedDate,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        readingStatus = readingStatus
    )
}

fun FavoriteBookEntity.toDomain(): FavoriteBooks {
    return FavoriteBooks(
        id = id,
        title = title,
        authors = authors,
        description = description,
        pageCount = pageCount,
        categories = categories,
        thumbnail = thumbnail,
        publisher = publisher,
        publishedDate = publishedDate,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        readingStatus = readingStatus
    )
}

