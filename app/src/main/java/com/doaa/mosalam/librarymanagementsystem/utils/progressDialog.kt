package com.doaa.mosalam.librarymanagementsystem.utils


import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.doaa.mosalam.librarymanagementsystem.R


class ProgressDialogHelper(private val context: Context) {

    private var dialog: Dialog? = null

    fun showProgressDialog() {
        if (dialog == null) {
            dialog = Dialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
            dialog?.setContentView(view)
            dialog?.setCancelable(false)
        }
        dialog?.show()
    }

    fun hideProgressDialog() {
        dialog?.dismiss()
    }
}