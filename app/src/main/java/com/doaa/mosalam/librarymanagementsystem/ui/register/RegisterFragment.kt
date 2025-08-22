package com.doaa.mosalam.librarymanagementsystem.ui.register

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentRegisterBinding
import com.doaa.mosalam.librarymanagementsystem.ui.register.viewModel.RegisterViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.InputValidator
import com.doaa.mosalam.librarymanagementsystem.utils.isEmailValid
import com.doaa.mosalam.librarymanagementsystem.utils.isNameValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPasswordValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPhoneValid

class RegisterFragment() : BasicFragment<FragmentRegisterBinding, RegisterViewModel>(),
    TextWatcher {
    override fun getLayoutResID(): Int = R.layout.fragment_register

    override val viewModel: RegisterViewModel = RegisterViewModel()
    private lateinit var checkIcon: Drawable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_check_24)!!

        initListener()
        initTextWatcher()
        initFocusListeners()
    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }

    }

    private fun initFocusListeners() {
        InputValidator.addFocusListener(binding.edNameRegister) { validateName() }
//        InputValidator.addFocusListener(binding.edPhoneRegister) { validatePhone() }
        InputValidator.addFocusListener(binding.edEmailRegister) { validateEmail() }
        InputValidator.addFocusListener(binding.edPasswordRegister) { validatePassword() }
        InputValidator.addFocusListener(binding.edConfirmPasswordRegister) { validateConfirmPassword() }
    }

    private fun initTextWatcher() {
        binding.edNameRegister.addTextChangedListener(this)
        binding.edPhoneRegister.addTextChangedListener(this)
        binding.edEmailRegister.addTextChangedListener(this)
        binding.edPasswordRegister.addTextChangedListener(this)
        binding.edConfirmPasswordRegister.addTextChangedListener(this)

    }

    private fun validateName(): Boolean {
        return InputValidator.validateField(
            binding.edNameRegister,
            binding.nameTilRegister,
            checkIcon,
            getString(R.string.name_is_require)
        ) { it.isNameValid() }
    }

//    private fun validatePhone(): Boolean {
//        return InputValidator.validateField(
//            binding.edPhoneRegister,
//            binding.phoneTilRegister,
//            checkIcon,
//            getString(R.string.phone_number_is_require)
//        ) { it.isPhoneValid() }
//    }

    private fun validateEmail(): Boolean {
        return InputValidator.validateField(
            binding.edEmailRegister,
            binding.emailTilRegister,
            checkIcon,
            getString(R.string.invalid_email_address)
        ) { it.isEmailValid() }
    }

    private fun validatePassword(): Boolean {
        return InputValidator.validateField(
            binding.edPasswordRegister,
            binding.passwordTilRegister,
            checkIcon,
            getString(R.string.oops_your_password_is_not_correct)
        ) { it.isPasswordValid() }
    }

    private fun validateConfirmPassword(): Boolean {
        val password = binding.edPasswordRegister.text.toString().trim()
        return InputValidator.validateField(
            binding.edConfirmPasswordRegister,
            binding.confirmPasswordTilRegister,
            checkIcon,
            getString(R.string.passwords_do_not_match)
        ) { it == password }
    }

    override fun afterTextChanged(s: Editable?) {}

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