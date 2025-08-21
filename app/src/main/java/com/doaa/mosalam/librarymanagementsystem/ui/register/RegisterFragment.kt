package com.doaa.mosalam.librarymanagementsystem.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentRegisterBinding
import com.doaa.mosalam.librarymanagementsystem.ui.login.viewModel.LoginViewModel
import com.doaa.mosalam.librarymanagementsystem.ui.register.viewModel.RegisterViewModel

class RegisterFragment() : BasicFragment<FragmentRegisterBinding, RegisterViewModel>() {
    override fun getLayoutResID(): Int = R.layout.fragment_register

    override val viewModel: RegisterViewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


}