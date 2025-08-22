package com.doaa.mosalam.librarymanagementsystem.ui.login

import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentLoginBinding
import com.doaa.mosalam.librarymanagementsystem.ui.login.viewModel.LoginViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.CommonClickListener
import com.doaa.mosalam.librarymanagementsystem.utils.CrashlyticsUtils
import com.doaa.mosalam.librarymanagementsystem.utils.InputValidator
import com.doaa.mosalam.librarymanagementsystem.utils.LoginException
import com.doaa.mosalam.librarymanagementsystem.utils.isEmailValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPasswordValid
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BasicFragment<FragmentLoginBinding, LoginViewModel>(), TextWatcher {
    private val vm: LoginViewModel by viewModels()

    override val viewModel: LoginViewModel
        get() = vm


    override fun getLayoutResID(): Int = R.layout.fragment_login


    private lateinit var checkIcon: Drawable
    private lateinit var progressDialog: ProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_check_24)!!
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)
        initListener()
        initTextWatcher()
        initFocusListeners()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.isSignInEnabled.collect { binding.btnLogin.isEnabled = it }
        }

        binding.btnLogin.setOnClickListener { vm.loginUser() }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.uiState.collect { state ->
                when (state) {
                    is LoginViewModel.UiState.Loading -> {
                        //  Progress
                        progressDialog.show()
                    }

                    is LoginViewModel.UiState.Success -> {
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                        view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }

                    is LoginViewModel.UiState.Error -> {
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun logAuthIssueToCrashlytics(msg: String, provider: String) {
        CrashlyticsUtils.sendCustomLogToCrashlytics<LoginException>(
            msg,
            CrashlyticsUtils.LOGIN_KEY to msg,
            CrashlyticsUtils.LOGIN_PROVIDER to provider,
        )
    }

    private fun initListener() {

        val commonClickListener = CommonClickListener { v ->
            when (v.id) {
                R.id.btn_login -> v.findNavController()
                    .navigate(R.id.action_loginFragment_to_homeFragment)

                R.id.btn_create_count -> v.findNavController()
                    .navigate(R.id.action_loginFragment_to_registerFragment)

                R.id.btnForgotPassword -> v.findNavController()
                    .navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
        }
        binding.btnLogin.setOnClickListener(commonClickListener)
        binding.btnCreateCount.setOnClickListener(commonClickListener)
        binding.btnForgotPassword.setOnClickListener(commonClickListener)
    }

//    private fun navigateToHome() {
//        binding.btnLogin.setOnClickListener { v ->
//            v.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }
//    }
//
//    private fun navigateToRegister() {
//        binding.btnCreateCount.setOnClickListener { v ->
//            v.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//        }
//    }

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

//        binding.btnLogin.isEnabled =
//            binding.edEmailLogin.text!!.trim().toString().isNotEmpty()
//                    && binding.edPasswordLogin.text.toString().isNotEmpty()
//                    && validateEmail()
//                    && validatePassword()
        vm.onEmailChanged(binding.edEmailLogin.text.toString())
        vm.onPasswordChanged(binding.edPasswordLogin.text.toString())
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