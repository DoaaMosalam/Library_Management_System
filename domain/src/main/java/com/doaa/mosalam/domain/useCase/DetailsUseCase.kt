package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.repo.DetailsRepo
import javax.inject.Inject

class DetailsUseCase @Inject constructor(
    private val detailsRepo: DetailsRepo
) {
     suspend operator  fun invoke(bookId: String) : Volume = detailsRepo.getBookDetails(bookId)
}