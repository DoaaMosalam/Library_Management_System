package com.doaa.mosalam.librarymanagementsystem.common

import android.widget.PopupMenu
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.doaa.mosalam.librarymanagementsystem.R

abstract class BaseUserNameFragment<VB : ViewDataBinding, VM : ViewModel>
    : BasicFragment<VB, VM>() {
    // Call this method in onViewCreated of your Fragment and pass the TextView for username
    protected fun setupUserMenu(userName: TextView) {
        userName.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.user_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.profile -> {
                        navigateToProfile()
                        true
                    }

                    R.id.payments -> {
                        navigateToPayments()
                        true
                    }

                    R.id.logout -> {
                        handleLogout()
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }
    }

    // can be abstract if each Fragment has different navigation
    abstract fun navigateToProfile()
    abstract fun navigateToPayments()
    protected open fun handleLogout() {}
}