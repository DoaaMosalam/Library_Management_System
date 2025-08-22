package com.doaa.mosalam.librarymanagementsystem.ui.forgetPasword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentForgetPasswordBinding
import com.doaa.mosalam.librarymanagementsystem.ui.forgetPasword.viewModel.ForgetViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.CommonClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BasicFragment<FragmentForgetPasswordBinding, ForgetViewModel>() {
    override fun getLayoutResID(): Int = R.layout.fragment_forget_password
    private val vm: ForgetViewModel by viewModels()

    override val viewModel: ForgetViewModel
        get() = vm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    private fun initListener() {
        val commonClickListener = CommonClickListener { v ->
            when (v.id) {
                R.id.btnResetPassword -> v.findNavController()
                    .navigate(R.id.action_forgetPasswordFragment_to_loginFragment)
            }
        }
        binding.btnResetPassword.setOnClickListener(commonClickListener)
    }


}