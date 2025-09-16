package com.doaa.mosalam.librarymanagementsystem.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentProfileBinding
import com.doaa.mosalam.librarymanagementsystem.ui.profile.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseUserNameFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun getLayoutResID(): Int = R.layout.fragment_profile
    private lateinit var userPreferences: UserPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)

        // init preferences
        userPreferences = UserPreferences(requireContext())

        val savedEmail = userPreferences.getUserEmail()

        if (!savedEmail.isNullOrEmpty()) {

            viewModel.loadUser(savedEmail)
        }

        observeUserData()

        updatePassword()

        deleteAccount()

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }


    }

    private fun deleteAccount() {
        binding.btnDeleteAccount.setOnClickListener {
            val savedUser = userPreferences.getUser()

            if (savedUser.email.isNotEmpty()) {
                //dialog alert
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Account")
                    .setMessage("Are you sure you want to delete your account?")
                    .setPositiveButton("Yes") { _, _ ->
                        // delete from room database
                        viewModel.deleteUser(savedUser.email)

                        // 🟢 delete from  SharedPreferences
                        userPreferences.clearUser()

                        Toast.makeText(requireContext(), "Account deleted", Toast.LENGTH_SHORT)
                            .show()

                        // 🟢 go to  LoginFragment
                        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            } else {
                Toast.makeText(requireContext(), "No user found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePassword() {
        binding.btnSaveNewPassword.setOnClickListener {
            val oldPass = binding.etOldPassword.text.toString().trim()
            val newPass = binding.etNewPassword.text.toString().trim()
            val confirmPass = binding.etConfirmPassword.text.toString().trim()

            val savedUser = userPreferences.getUser()

            if (oldPass != savedUser.password) {
                Toast.makeText(requireContext(), "Old password is incorrect", Toast.LENGTH_SHORT)
                    .show()
            } else if (newPass != confirmPass) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.changePassword(savedUser.email, newPass)
                userPreferences.saveUser(
                    name = savedUser.name,
                    email = savedUser.email,
                    phone = savedUser.phone,
                    password = newPass // update shared prefs too
                )
            }
        }

        binding.btnCancel.setOnClickListener {
            binding.etOldPassword.text?.clear()
            binding.etNewPassword.text?.clear()
            binding.etConfirmPassword.text?.clear()
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeUserData() {
        // user data
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.collect { user ->
                if (user != null) {
                    binding.tvUserName.text = user.name
                    binding.etUsername.text = user.name
                    binding.tvUserEmail.text = user.email
                    binding.tvUserPhone.text = user.phone
                } else {
                    // fallback لو Room مرجعش حاجة
                    val localUser = userPreferences.getUser()
                    binding.tvUserName.text = localUser.name
                    binding.tvUserEmail.text = localUser.email
                    binding.tvUserPhone.text = localUser.phone
                }
            }
        }

        // loading state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collect { isLoading ->
                binding.commonHeader.progressIndicator.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }

        // error state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { errorMsg ->
                if (!errorMsg.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        // update password
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.passwordChangeState.collect { msg ->
                msg?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    override fun navigateToProfile() {
        return // already in profile

    }

    override fun navigateToPayments() {
        findNavController().navigate(R.id.action_profileFragment_to_paymentFragment)
    }
}