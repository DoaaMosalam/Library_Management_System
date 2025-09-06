package com.doaa.mosalam.librarymanagementsystem.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentProfileBinding
import com.doaa.mosalam.librarymanagementsystem.ui.profile.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseUserNameFragment<FragmentProfileBinding, ProfileViewModel>() {
    override fun getLayoutResID(): Int = R.layout.fragment_profile
    private val vm: ProfileViewModel by viewModels()
    override val viewModel: ProfileViewModel
        get() = vm


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)


    }

    override fun navigateToProfile() {
        return // already in profile

    }

    override fun navigateToPayments() {
        TODO("Not yet implemented")
    }
}