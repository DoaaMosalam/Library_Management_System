package com.doaa.mosalam.librarymanagementsystem.ui.mySelf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentMySelfBinding
import com.doaa.mosalam.librarymanagementsystem.ui.mySelf.viewModel.MySelfViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySelfFragment : BaseUserNameFragment<FragmentMySelfBinding, MySelfViewModel>() {
    override fun getLayoutResID(): Int = R.layout.fragment_my_self

    override val viewModel: MySelfViewModel
        get() = vm


    override fun navigateToProfile() {
        findNavController().navigate(R.id.action_mySelfFragment_to_profileFragment)

    }

    override fun navigateToPayments() {
        TODO("Not yet implemented")
    }
    private val vm : MySelfViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)
    }




}