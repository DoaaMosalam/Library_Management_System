package com.doaa.mosalam.data.remote

import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    /*
    https://www.googleapis.com/books/v1/volumes?q=allbooks
    *
    * or
    * https://80787bd1b4d3.ngrok-free.app/trending-books/most-loaned

    * */

    // get all trending books
    @GET("volumes")
    suspend fun getTrendingBooks(
        @Query("q") query: String = "bestsellers",
        @Query("maxResults") maxResults: Int = 40
    ): BooksResponse

//    @GET("trending-books/most-loaned")
//    suspend fun getTrendingBooks(
//        @Query("q") query: String = "bestsellers",
//        @Query("maxResults") maxResults: Int = 40
//
//    ): BooksResponse

    // get books by category
    @GET("volumes")
    suspend fun getBooksByCategory(
        @Query("q") category: String,
        @Query("maxResults") maxResults: Int = 40
    ): BooksResponse





    // Search Books by query
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40
    ): BooksResponse

    // Get Book Details by ID
    @GET("volumes/{id}")
    suspend fun getBookDetails(
        @retrofit2.http.Path("id") id: String
    ): BooksResponse

}