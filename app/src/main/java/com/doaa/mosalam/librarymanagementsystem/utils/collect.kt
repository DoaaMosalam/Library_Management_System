package com.doaa.mosalam.librarymanagementsystem.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
fun <T> Flow<T>.collectIn(lifecycleOwner: LifecycleOwner, collector: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectIn.collect {
            collector(it)
        }
    }
}