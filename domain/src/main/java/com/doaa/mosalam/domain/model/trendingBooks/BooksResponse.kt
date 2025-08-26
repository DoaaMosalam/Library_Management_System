package com.doaa.mosalam.domain.model.trendingBooks


data class BooksResponse(
    val kind: String?,
    val totalItems: Int?,
    val items: List<Volume?>? = null
)
