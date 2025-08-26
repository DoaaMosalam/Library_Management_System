package com.doaa.mosalam.data.remote

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

import retrofit2.Response

interface APIService {

    /*
    https://www.googleapis.com/books/v1/volumes?q=allbooks
    *
    * or
    * https://gutendex.com/books/

    * */

    // get all trending books
    @GET("volumes")
    suspend fun getTrendingBooks(
        @Query("q") query: String = "bestsellers",
        @Query("maxResults") maxResults: Int = 20
    ): BooksResponse



    // Search Books by query
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20
    ): BooksResponse

    // Get Book Details by ID
    @GET("volumes/{id}")
    suspend fun getBookDetails(
        @retrofit2.http.Path("id") id: String
    ): BooksResponse

}