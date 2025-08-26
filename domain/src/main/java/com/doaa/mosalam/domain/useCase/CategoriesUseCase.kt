package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.repo.CategoriesRepo
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) {
    suspend fun getBooksByCategory(category: String) = categoriesRepo.getBooksByCategory(category)

}