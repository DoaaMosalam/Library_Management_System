package com.doaa.mosalam.data.repository


import com.doaa.mosalam.data.remote.APIService
import com.doaa.mosalam.domain.model.trendingBooks.BooksResponse
import com.doaa.mosalam.domain.repo.BooksRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BooksRepoImp @Inject constructor(
    private val apiService: APIService
) : BooksRepo {

    override suspend fun getTrendingBooks(): BooksResponse {

        return try {
            apiService.getTrendingBooks()
        } catch (e: IOException) {
            BooksResponse(kind = null, totalItems = 0, items = emptyList())
        } catch (e: HttpException) {
            BooksResponse(kind = null, totalItems = 0, items = emptyList())
        }
    }



    //    override suspend fun getTrendingBooks(): Flow<List<Books>> = flow {
//        emit(
//            listOf(
//                Books(
//                    id = "1",
//                    title = "Clean Code",
//                    author = "Robert C. Martin",
//                    coverImageUrl = "https://books.google.com/books/content?id=FBl2EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                    loanCount = 120,
//                    rating = 4.8f,
//                    reviewCount = 500,
//                    createdAt = "2008"
//                ),
//                Books(
//                    id = "2",
//                    title = "Effective Java",
//                    author = "Joshua Bloch",
//                    coverImageUrl = "https://books.google.com/books/content?id=FBl2EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                    loanCount = 90,
//                    rating = 4.6f,
//                    reviewCount = 350,
//                    createdAt = "2018"
//                ),
//                Books(
//                    id = "2",
//                    title = "Effective Java",
//                    author = "Joshua Bloch",
//                    coverImageUrl = "https://books.google.com/books/content?id=FBl2EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                    loanCount = 90,
//                    rating = 4.6f,
//                    reviewCount = 350,
//                    createdAt = "2018"
//                ),
//                Books(
//                    id = "2",
//                    title = "Effective Java",
//                    author = "Joshua Bloch",
//                    coverImageUrl = "https://books.google.com/books/content?id=FBl2EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                    loanCount = 90,
//                    rating = 4.6f,
//                    reviewCount = 350,
//                    createdAt = "2018"
//                )
//            )
//        )
//    }
//

}
