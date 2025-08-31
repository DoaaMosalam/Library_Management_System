package com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.useCase.BooksUseCase
import com.doaa.mosalam.domain.useCase.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksUseCase: BooksUseCase,
    private val categoryUseCase: CategoriesUseCase,
) : ViewModel() {
    private val _books = MutableStateFlow<List<Volume>?>(null)
    val books: StateFlow<List<Volume>?> = _books

    private val _isLoading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _booksByCategory = MutableStateFlow<List<Volume>>(emptyList())
    val booksByCategory: StateFlow<List<Volume>> = _booksByCategory

    private val _favoriteBooks = MutableStateFlow<List<Volume>>(emptyList())
    val favoriteBooks: StateFlow<List<Volume>> = _favoriteBooks

    init {
        getTrendingBooks()
    }

    fun getTrendingBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = booksUseCase.getTrendingBooks()
                Log.d("HomeViewModel", "Books count: ${response.items?.size}")
                _books.value = (response.items ?: emptyList()) as List<Volume>?

                val allCategories = response.items
                    ?.flatMap { it?.volumeInfo?.categories ?: emptyList() }
                    ?.distinct()
                    ?: emptyList()
                _categories.value = allCategories

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBooksByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = categoryUseCase.getBooksByCategory(category)
                _booksByCategory.value = (response.items ?: emptyList()) as List<Volume>
                Log.d("HomeViewModel", "Books for $category: ${response.items?.size}")
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(book: Volume) {
        val currentFavorites = _favoriteBooks.value.toMutableList()
        if (currentFavorites.any { it.id == book.id }) {
            // remove from favorites
            currentFavorites.removeAll { it.id == book.id }
        } else {
            currentFavorites.add(book)
        }
        _favoriteBooks.value = currentFavorites
    }

    fun isBookFavorite(bookId: String): Boolean {
        return _favoriteBooks.value.any { it.id == bookId }
    }


//=================================================================================================
//    private val _books = MutableStateFlow<List<Books>>(emptyList())
//    val books: StateFlow<List<Books>> = _books
//
//    private val _isLoading = MutableStateFlow(false)
//    val loading: StateFlow<Boolean> = _isLoading
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//    init {
//        getTrendingBooks()
//    }
//
//    fun getTrendingBooks() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _isLoading.value = true
//            try {
//                booksUseCase.getTrendingBooks().collect { list ->
//                    _books.value = list
//                }
//            } catch (e: Exception) {
//                _error.value = e.message
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

}