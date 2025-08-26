package com.doaa.mosalam.domain.model.favorite

data class FavoriteBooks(
    val id: String?=null,
    val title: String?=null,
    val authors: List<String>?=null,
    val publisher: String?=null,
    val publishedDate: String?=null,
    val description: String?=null,
    val pageCount: Int?=null,
    val categories: List<String>?=null,
    val averageRating: Double?=null,
    val ratingsCount: Int?=null,
    val thumbnail: String?=null
)