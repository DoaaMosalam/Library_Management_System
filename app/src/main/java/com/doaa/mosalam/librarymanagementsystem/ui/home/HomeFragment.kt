package com.doaa.mosalam.librarymanagementsystem.ui.home

import android.os.Bundle
import android.view.View
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentHomeBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel

class HomeFragment : BasicFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel = HomeViewModel()

    override fun getLayoutResID(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}