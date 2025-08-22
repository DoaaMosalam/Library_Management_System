package com.doaa.mosalam.librarymanagementsystem.utils

import android.view.View

class CommonClickListener(
    private val onClickAction: (View) -> Unit
) : View.OnClickListener {
    override fun onClick(v: View?) {
        v?.let { onClickAction(it) }
    }
}