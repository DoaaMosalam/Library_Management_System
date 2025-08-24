package com.doaa.mosalam.librarymanagementsystem.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.EditText
import com.doaa.mosalam.librarymanagementsystem.R
import com.google.android.material.textfield.TextInputLayout

object InputValidator {
    fun validateField(
        editText: EditText,
        textInputLayout: TextInputLayout,
        checkIcon: Drawable,
        errorMessage: String,
        validator: (String) -> Boolean
    ): Boolean {
        val value = editText.text.toString().trim()

        return if (!validator(value)) {
            textInputLayout.error = errorMessage
            textInputLayout.endIconDrawable = null
            false
        } else {
            textInputLayout.apply {
                error = null
                endIconDrawable = checkIcon
                setStartIconDrawable(R.drawable.baseline_check_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
            true
        }
    }

    fun addFocusListener(
        editText: EditText,
        validatorAction: () -> Boolean
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatorAction()
            }
        }
    }
}