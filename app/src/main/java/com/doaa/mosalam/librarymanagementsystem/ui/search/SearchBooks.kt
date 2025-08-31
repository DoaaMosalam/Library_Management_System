package com.doaa.mosalam.librarymanagementsystem.ui.search

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun EditText.debounceSearch(
    lifecycleOwner: LifecycleOwner,
    delayMillis: Long = 300,
    onQueryChanged: (String) -> Unit
) {
    var searchJob: Job? = null

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val query = s?.toString()?.trim().orEmpty()
            searchJob?.cancel()
            searchJob = lifecycleOwner.lifecycleScope.launch {
                delay(delayMillis)
                onQueryChanged(query)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}