package com.doaa.mosalam.librarymanagementsystem.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentLoginBinding
import com.doaa.mosalam.librarymanagementsystem.ui.login.viewModel.LoginViewModel
import androidx.navigation.findNavController


class LoginFragment : BasicFragment<FragmentLoginBinding, LoginViewModel>(){
    override fun getLayoutResID(): Int = R.layout.fragment_login

    override val viewModel: LoginViewModel = LoginViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        navigateToHome()
        navigateToRegister()
    }
    private fun navigateToHome() {
        binding.btnLogin.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun navigateToRegister() {
        binding.btnCreateCount.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}