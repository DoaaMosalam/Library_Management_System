package com.doaa.mosalam.data.mapper

import com.doaa.mosalam.data.local.favoriteBooks.FavoriteBookEntity
import com.doaa.mosalam.domain.model.favorite.FavoriteBooks


// Mapping Function
fun FavoriteBooks.mapToFavoriteDB(): FavoriteBookEntity {
    return FavoriteBookEntity(
        id = id!!,
        title = title,
        authors = authors?.joinToString(","),
        description = description,
        pageCount = pageCount,
        categories = categories?.joinToString(","),
        thumbnail = thumbnail,
        publisher = null,
        publishedDate = null,
        averageRating = null,
        ratingsCount = null
    )
}

fun FavoriteBookEntity.toDomain(): FavoriteBooks {
    return FavoriteBooks(
        id = id,
        title = title,
        authors = authors?.split(","),
        description = description,
        pageCount = pageCount,
        categories = categories?.split(","),
        thumbnail = thumbnail
    )
}
