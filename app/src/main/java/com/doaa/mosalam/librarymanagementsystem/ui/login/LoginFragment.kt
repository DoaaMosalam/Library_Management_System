package com.doaa.mosalam.librarymanagementsystem.ui.login

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import com.doaa.mosalam.librarymanagementsystem.utils.showSnakeBarError
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BasicFragment<FragmentLoginBinding, LoginViewModel>(), TextWatcher {
    private val vm: LoginViewModel by viewModels()

    override val viewModel: LoginViewModel
        get() = vm


    override fun getLayoutResID(): Int = R.layout.fragment_login

    private lateinit var checkIcon: Drawable
    private lateinit var progressDialog: ProgressDialog
    private val loginManager: LoginManager by lazy { LoginManager.getInstance() }
    private val callbackManager: CallbackManager by lazy { CallbackManager.Factory.create() }
    val progressBar = view?.findViewById<ProgressBar>(R.id.progressIndicator)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_check_24)!!
        // Initialize the ProgressDialog
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
//                        progressBar?.visibility = View.VISIBLE
                    }

                    is LoginViewModel.UiState.Success -> {
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                        progressBar?.visibility = View.GONE
                        view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }

                    is LoginViewModel.UiState.Error -> {
                        if (progressDialog.isShowing) {
                            progressDialog.dismiss()
                        }
                        progressBar?.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }


        // زر تسجيل الدخول العادي (Email + Password)
        binding.btnLogin.setOnClickListener {
            vm.loginUser()
        }

    }  // end of onViewCreated

    //==============================================================================================
    private fun logAuthIssueToCrashlytics(msg: String, provider: String) {
        CrashlyticsUtils.sendCustomLogToCrashlytics<LoginException>(
            msg,
            CrashlyticsUtils.LOGIN_KEY to msg,
            CrashlyticsUtils.LOGIN_PROVIDER to provider,
        )
    }
// ==============================================================================================
    // Initialize Click Listeners

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
        // Google login
        binding.btnGoogle.setOnClickListener {
            loginWithGoogleRequest()

        }
        //  Facebook login
        binding.btnFacebook.setOnClickListener {
            loginWithFacebook()
        }
    }

//==============================================================================================
    /**
     *  * Google Sign-In
     *
     * */
    // handle code login with google.
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            } else {
                view?.showSnakeBarError(getString(R.string.google_sign_in_failed_msg))
            }
        }

    private fun loginWithGoogleRequest() {
        val signInIntent = getGoogleRequestIntent(requireActivity())
        launcher.launch(signInIntent)
    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            firebaseAuthWithGoogle(account.idToken!!)
//            binding.btnLogin.setOnClickListener { v ->
//                v.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            }
//        } catch (e: Exception) {
//            view?.showSnakeBarError(e.message ?: getString(R.string.generic_error_msg))
//            val msg = e.message ?: getString(R.string.generic_error_msg)
//            logAuthIssueToCrashlytics(msg, "Google")
//        }
//    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: Exception) {
            view?.showSnakeBarError(e.message ?: getString(R.string.generic_error_msg))
            val msg = e.message ?: getString(R.string.generic_error_msg)
            logAuthIssueToCrashlytics(msg, "Google")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        viewModel.loginWithGoogle(idToken)
    }

//    private fun firebaseAuthWithGoogle(idToken: String) {
//        viewModel.loginWithGoogle(idToken)
//        binding.btnLogin.setOnClickListener { v ->
//            v.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }
//
//    }


    //=======================================================================================
    // handle code login with facebook.
    private fun signOut() {
        loginManager.logOut()
        Log.d(TAG, "signOut: ")
    }

    private fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    private fun loginWithFacebook() {
        if (isLoggedIn()) signOut()
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val token = result.accessToken.token
                Log.d(TAG, "onSuccess: $token")
                firebaseAuthWithFacebook(token)
            }

            override fun onCancel() {
                // Handle login cancel
            }

            override fun onError(error: FacebookException) {
                // Handle login error
                val msg = error.message ?: getString(R.string.generic_error_msg)
                Log.d(TAG, "onError: $msg")
                view?.showSnakeBarError(msg)
                logAuthIssueToCrashlytics(msg, "Facebook")
            }
        })

        loginManager.logInWithReadPermissions(
            this, callbackManager, listOf("email", "public_profile")
        )
    }

    private fun firebaseAuthWithFacebook(token: String) {
        viewModel.loginWithFacebook(token)
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

