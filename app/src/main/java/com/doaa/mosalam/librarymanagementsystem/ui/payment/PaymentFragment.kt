package com.doaa.mosalam.librarymanagementsystem.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentPaymentBinding
import com.doaa.mosalam.librarymanagementsystem.ui.payment.viewModel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseUserNameFragment<FragmentPaymentBinding, PaymentViewModel>() {
    override val viewModel: PaymentViewModel by viewModels()

    override fun getLayoutResID(): Int = R.layout.fragment_payment
    override fun navigateToProfile() {
        findNavController().navigate(R.id.action_paymentFragment_to_profileFragment)
    }

    override fun navigateToPayments() {
        return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}