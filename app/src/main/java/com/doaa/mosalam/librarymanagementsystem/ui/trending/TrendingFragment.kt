package com.doaa.mosalam.librarymanagementsystem.ui.trending

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.BooksAdapter
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentTrendingBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.CommonClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : BaseUserNameFragment<FragmentTrendingBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()


    override fun navigateToProfile() {
        findNavController().navigate(R.id.action_trendingFragment_to_profileFragment)
    }

    override fun navigateToPayments() {
        TODO("Not yet implemented")
    }

    override fun getLayoutResID(): Int = R.layout.fragment_trending
    private lateinit var adapter: BooksAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)
        // init listeners
        initListener()

        setupAdapter()

        setupObservers()

        viewModel.getTrendingBooks()

    }

    private fun initListener() {
        val commonClick = CommonClickListener { v ->
            when (v.id) {
                R.id.btn_myShelf -> v.findNavController()
                    .navigate(R.id.action_trendingFragment_to_mySelfFragment)

            }
        }
        binding.commonHeader.btnMyShelf.setOnClickListener(commonClick)
    }

    private fun setupAdapter() {
        adapter = BooksAdapter(
            onRentClick = { book ->
                // TODO: handle rent click
            },
            onFavClick = { book ->
                viewModel.toggleFavorite(book)
            },
            onItemClick = { book ->
                book.id?.let { id ->
                    val action =
                        TrendingFragmentDirections.actionTrendingFragmentToDetailsFragment(id)
                    findNavController().navigate(action)

                }
            },
            onCheckFavorite = { bookId ->
                viewModel.isBookFavorite(bookId)
            },
            lifecycleOwner = viewLifecycleOwner
        )
        binding.rvTrendingBooksPage.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.books.collectLatest { list ->
                adapter.setData(list ?: emptyList())
            }
        }

        lifecycleScope.launch {
            viewModel.loading.collectLatest { loading ->
                binding.progressIndicator.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { message ->
                message?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.favoriteBooks.collect { favorites ->
                adapter.notifyDataSetChanged()
            }
        }
    }


}