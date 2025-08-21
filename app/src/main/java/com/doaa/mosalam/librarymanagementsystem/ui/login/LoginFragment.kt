package com.doaa.mosalam.librarymanagementsystem.ui.login

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentLoginBinding
import com.doaa.mosalam.librarymanagementsystem.ui.login.viewModel.LoginViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.CrashlyticsUtils
import com.doaa.mosalam.librarymanagementsystem.utils.InputValidator
import com.doaa.mosalam.librarymanagementsystem.utils.LoginException
import com.doaa.mosalam.librarymanagementsystem.utils.isEmailValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPasswordValid


class LoginFragment : BasicFragment<FragmentLoginBinding, LoginViewModel>(), TextWatcher {
    override fun getLayoutResID(): Int = R.layout.fragment_login

    override val viewModel: LoginViewModel = LoginViewModel()
    private lateinit var checkIcon: Drawable


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_check_24)!!

        initListener()
        initTextWatcher()
        initFocusListeners()
    }

    private fun logAuthIssueToCrashlytics(msg: String, provider: String) {
        CrashlyticsUtils.sendCustomLogToCrashlytics<LoginException>(
            msg,
            CrashlyticsUtils.LOGIN_KEY to msg,
            CrashlyticsUtils.LOGIN_PROVIDER to provider,
        )
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

    //==============================================================================================
    // TextWatcher methods
    private fun initTextWatcher() {
        binding.edEmailLogin.addTextChangedListener(this)
        binding.edPasswordLogin.addTextChangedListener(this)
    }

    private fun initFocusListeners() {
        InputValidator.addFocusListener(binding.edEmailLogin) { validateEmail() }
        InputValidator.addFocusListener(binding.edPasswordLogin) { validatePassword() }
    }

    private fun validateEmail(): Boolean {
        return InputValidator.validateField(
            binding.edEmailLogin,
            binding.emailTilLogin,
            checkIcon,
            getString(R.string.invalid_email_address)
        ) { it.isEmailValid() }
    }

    private fun validatePassword(): Boolean {
        return InputValidator.validateField(
            binding.edPasswordLogin,
            binding.passworrdTilLogin,
            checkIcon,
            getString(R.string.oops_your_password_is_not_correct)
        ) { it.isPasswordValid() }
    }


    override fun afterTextChanged(s: Editable?) {

        binding.btnLogin.isEnabled =
            binding.edEmailLogin.text!!.trim().toString().isNotEmpty()
                    && binding.edPasswordLogin.text.toString().isNotEmpty()
                    && validateEmail()
                    && validatePassword()
    }

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
    }


}