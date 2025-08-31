package com.doaa.mosalam.librarymanagementsystem.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.useCase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Volume>>(emptyList())
    val searchResults: StateFlow<List<Volume>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchBooks(query: String) {
        if (query.isBlank()) return  // Ignore empty queries

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = searchUseCase.searchBooks(query)

                val items = response.items?.filterNotNull() ?: emptyList()
                Log.d("SearchViewModel", "Books count: ${items.size}")

                _searchResults.value = items
                _error.value = null

            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error: ${e.message}", e)
                _error.value = e.message ?: "Unexpected error"
                _searchResults.value = emptyList() // Clear results on error
            } finally {
                _isLoading.value = false
            }
        }
    }
}