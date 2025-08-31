package com.doaa.mosalam.librarymanagementsystem.ui.trending

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.BooksAdapter
import com.doaa.mosalam.librarymanagementsystem.adapter.CategoriesAdapter
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentTrendingBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : BaseUserNameFragment<FragmentTrendingBinding, HomeViewModel>() {
    private val vm: HomeViewModel by viewModels()

    override val viewModel: HomeViewModel
        get() = vm


    override fun getLayoutResID(): Int = R.layout.fragment_trending
    private lateinit var adapter: BooksAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)

        setupAdapter()
        setupObservers()

        vm.getTrendingBooks()

    }
    private fun setupAdapter() {
        adapter = BooksAdapter(
            onRentClick = { book ->
                // TODO: handle rent click
            },
            onFavClick = { book ->
                vm.toggleFavorite(book)
            },
            onItemClick = { book ->
//                val action = HomeFragmentDirections.actionHomeFragmentToBookDetailsFragment(book.id ?: "")
//                findNavController().navigate(action)
            },
            onCheckFavorite = { bookId ->
                vm.isBookFavorite(bookId)
            }
        )
//        binding.rvTrendingBooksPage.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendingBooksPage.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            vm.books.collectLatest { list ->
                adapter.setData(list ?: emptyList())
            }
        }

        lifecycleScope.launch {
            vm.loading.collectLatest { loading ->
                binding.progressIndicator.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            vm.error.collectLatest { message ->
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