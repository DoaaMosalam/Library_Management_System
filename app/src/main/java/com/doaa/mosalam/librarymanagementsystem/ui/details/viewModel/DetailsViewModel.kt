package com.doaa.mosalam.librarymanagementsystem.ui.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.domain.useCase.DetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: DetailsUseCase,

    ) : ViewModel() {
    private val _bookDetails = MutableStateFlow<Volume?>(null)
    val bookDetails: MutableStateFlow<Volume?> = _bookDetails

    private val _isLoading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getBookDetails(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val response = detailsUseCase(bookId)
                _bookDetails.value = response
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

}