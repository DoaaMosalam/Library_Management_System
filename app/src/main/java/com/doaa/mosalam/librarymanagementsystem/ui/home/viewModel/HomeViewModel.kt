package com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.useCase.BooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksUseCase: BooksUseCase
) : ViewModel() {
    private val _books = MutableStateFlow<List<Volume>?>(null)
    val books: StateFlow<List<Volume>?> = _books

    private val _isLoading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
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