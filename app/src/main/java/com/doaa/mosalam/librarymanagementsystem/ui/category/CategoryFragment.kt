package com.doaa.mosalam.librarymanagementsystem.ui.category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.BooksAdapter
import com.doaa.mosalam.librarymanagementsystem.adapter.CategoriesAdapter
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentCategoryBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseUserNameFragment<FragmentCategoryBinding, HomeViewModel>() {
    override fun getLayoutResID(): Int = R.layout.fragment_category
    private val vm: HomeViewModel by viewModels()

    override val viewModel: HomeViewModel
        get() = vm

    private lateinit var booksAdapter: BooksAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)
        setupAdapter()
        // set adapter for categories
        setupCategoryAdapter()

        setupObservers()
    }

    override fun navigateToProfile() {
        findNavController().navigate(R.id.action_categoryFragment_to_profileFragment)
    }

    override fun navigateToPayments() {
        TODO("Not yet implemented")
    }
    private fun setupAdapter() {
        booksAdapter = BooksAdapter(
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
        binding.rvCategoriesPage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoriesPage.adapter = booksAdapter
    }

    // set adapter for categories
    private fun setupCategoryAdapter() {
        categoriesAdapter = CategoriesAdapter(emptyList()) { category ->
            vm.getBooksByCategory(category)
        }
        binding.rvCategoriesPage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoriesPage.adapter = categoriesAdapter
        binding.rvCategoriesPage.setHasFixedSize(true)


    }

    private fun setupObservers() {
        lifecycleScope.launch {
            vm.books.collectLatest { list ->
                booksAdapter.setData(list ?: emptyList())
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
            vm.categories.collectLatest { list ->
                categoriesAdapter = CategoriesAdapter(list) { category ->
                    vm.getBooksByCategory(category)
                }
                binding.rvCategoriesPage.adapter = categoriesAdapter

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.booksByCategory.collect { books ->

                    booksAdapter.setData(books)
                    binding.rvCategoriesBooksPage.adapter = booksAdapter
                }
            }
        }
        lifecycleScope.launch {
            viewModel.favoriteBooks.collect { favorites ->
                booksAdapter.notifyDataSetChanged()
            }
        }
    }




}