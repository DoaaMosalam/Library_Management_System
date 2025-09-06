package com.doaa.mosalam.data.repository

import android.content.res.Resources
import com.doaa.mosalam.data.remote.APIService
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.repo.DetailsRepo
import javax.inject.Inject

class DetailsRepoImp @Inject constructor(
    private val apiService: APIService
) : DetailsRepo {
    override suspend fun getBookDetails(bookId: String): Volume {
        return apiService.getBookDetails(bookId)
    }


}