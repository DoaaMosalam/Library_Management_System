package com.doaa.mosalam.librarymanagementsystem.ui.mySelf.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import com.doaa.mosalam.domain.useCase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySelfViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
): ViewModel() {
    private val _favoriteBooks = MutableStateFlow<List<FavoriteBooks>>(emptyList())
    val favoriteBooks: StateFlow<List<FavoriteBooks>> = _favoriteBooks.asStateFlow()

    init {

        observeFavorites()
    }
     fun observeFavorites(){
        viewModelScope.launch {
            favoriteUseCase.getBooksFromFavorites().collect { books ->
                _favoriteBooks.value= books
            }

        }
    }
    fun changeReadingStatus(book: FavoriteBooks, newStatus: String) {
        viewModelScope.launch {
            // ببساطة: احذفي القديم + دخلي نسخة جديدة بالحالة الجديدة
//            val updatedBook = book.copy(readingStatus = newStatus)
//            favoriteUseCase.addBooksToFavorites(updatedBook)
            favoriteUseCase.updateReadingStatus(
                bookId = book.id!!,
                status = newStatus
            )
        }
    }

    fun removeFromFavorites(bookId: FavoriteBooks) {
        viewModelScope.launch {
            favoriteUseCase.removeFromFavorites(bookId.toString())
        }
    }
}