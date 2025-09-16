package com.doaa.mosalam.librarymanagementsystem.ui.mySelf

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.FavoriteBooksAdapter
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentMySelfBinding
import com.doaa.mosalam.librarymanagementsystem.ui.mySelf.viewModel.MySelfViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MySelfFragment : BaseUserNameFragment<FragmentMySelfBinding, MySelfViewModel>() {
    override val viewModel: MySelfViewModel by viewModels()

    override fun getLayoutResID(): Int = R.layout.fragment_my_self

    override fun navigateToProfile() {
        findNavController().navigate(R.id.action_mySelfFragment_to_profileFragment)

    }

    override fun navigateToPayments() {
        findNavController().navigate(R.id.action_mySelfFragment_to_paymentFragment)
    }

    //    val tabLayout: TabLayout by lazy { binding.tabLayout }
//    val viewPager: ViewPager2 by lazy { binding.viewPager }
    private lateinit var fVBookAdapter: FavoriteBooksAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)
        // set adapter for Favorite Books
        setUpFavoriteAdapter()

        setupObservers()

        viewModel.observeFavorites()


//        val adapter = BooksPagerAdapter(this)
//        viewPager.adapter = adapter
//
//        tabStatueReading()

    }
    // create table layout include(All Books, Currently Reading, Rented, Finished)

//    private fun tabStatueReading() {
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when (position) {
//                0 -> tab.text = "All Books"
//                1 -> tab.text = "Currently Reading"
//                2 -> tab.text = "Rented"
//                3 -> tab.text = "Finished"
//            }
//        }.attach()
//    }

    private fun setUpFavoriteAdapter() {
        fVBookAdapter = FavoriteBooksAdapter(
            onRemoveClick = { book -> viewModel.removeFromFavorites(book) },
            onStatusChange = { book, newStatus ->
                viewModel.changeReadingStatus(book, newStatus)
            }

        )
        binding.rvFVBooks.adapter = fVBookAdapter
        binding.rvFVBooks.setHasFixedSize(true)

    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.favoriteBooks.collect { list ->
                fVBookAdapter.setData(list)
            }
        }

    }


}