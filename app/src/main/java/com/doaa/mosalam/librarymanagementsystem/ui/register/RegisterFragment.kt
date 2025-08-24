package com.doaa.mosalam.librarymanagementsystem.ui.register


import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentRegisterBinding
import com.doaa.mosalam.librarymanagementsystem.ui.register.viewModel.RegisterViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.InputValidator
import com.doaa.mosalam.librarymanagementsystem.utils.isEmailValid
import com.doaa.mosalam.librarymanagementsystem.utils.isNameValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPasswordValid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BasicFragment<FragmentRegisterBinding, RegisterViewModel>(), TextWatcher {
    override fun getLayoutResID(): Int = R.layout.fragment_register

    private val vm: RegisterViewModel by viewModels()
    override val viewModel: RegisterViewModel
        get() = vm

    private lateinit var checkIcon: Drawable
    private lateinit var progressDialog: ProgressDialog
    val progressBar = view?.findViewById<ProgressBar>(R.id.progressIndicator)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the ProgressDialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        checkIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_check_24)!!

        initTextWatcher()
        initFocusListeners()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.isRegisterEnabled.collect { binding.btnRegister.isEnabled = it }
        }

        binding.btnRegister.setOnClickListener {
            vm.registerUser()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.uiState.collect { state ->
                when (state) {
                    is RegisterViewModel.UiState.Loading -> {

                        progressDialog.show()
                      progressBar?.visibility = View.VISIBLE

                    }

                    is RegisterViewModel.UiState.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Registration Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        view.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                        progressBar?.visibility = View.GONE
                    }

                    is RegisterViewModel.UiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun initTextWatcher() {
        binding.edNameRegister.addTextChangedListener(this)
        binding.edPhoneRegister.addTextChangedListener(this)
        binding.edEmailRegister.addTextChangedListener(this)
        binding.edPasswordRegister.addTextChangedListener(this)
        binding.edConfirmPasswordRegister.addTextChangedListener(this)
    }

    private fun initFocusListeners() {
        InputValidator.addFocusListener(binding.edNameRegister) { validateName() }
        InputValidator.addFocusListener(binding.edEmailRegister) { validateEmail() }
        InputValidator.addFocusListener(binding.edPasswordRegister) { validatePassword() }
        InputValidator.addFocusListener(binding.edConfirmPasswordRegister) { validateConfirmPassword() }
    }

    private fun validateName(): Boolean {
        return InputValidator.validateField(
            binding.edNameRegister,
            binding.nameTilRegister,
            checkIcon,
            getString(R.string.name_is_require)
        ) { it.isNameValid() }
    }

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

    override fun afterTextChanged(s: Editable?) {
        vm.onNameChanged(binding.edNameRegister.text.toString())
        vm.onPhoneChanged(binding.edPhoneRegister.text.toString())
        vm.onEmailChanged(binding.edEmailRegister.text.toString())
        vm.onPasswordChanged(binding.edPasswordRegister.text.toString())
        vm.onConfirmPasswordChanged(binding.edConfirmPasswordRegister.text.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
