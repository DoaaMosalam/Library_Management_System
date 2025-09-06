package com.doaa.mosalam.librarymanagementsystem.ui.category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.BooksAdapter
import com.doaa.mosalam.librarymanagementsystem.adapter.CategoriesAdapter
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentCategoryBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.CommonClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseUserNameFragment<FragmentCategoryBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()


    override fun getLayoutResID(): Int = R.layout.fragment_category
//    private val vm: HomeViewModel by viewModels()
//
//    override val viewModel: HomeViewModel
//        get() = vm

    private lateinit var booksAdapter: BooksAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)
        // init listeners
        initListener()

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

    private fun initListener() {
        val commonClick = CommonClickListener { v ->
            when (v.id) {
                R.id.btn_myShelf -> v.findNavController()
                    .navigate(R.id.action_categoryFragment_to_mySelfFragment)

            }
        }
        binding.commonHeader.btnMyShelf.setOnClickListener(commonClick)
    }

    private fun setupAdapter() {
        booksAdapter = BooksAdapter(
            onRentClick = { book ->
                // TODO: handle rent click
            },
            onFavClick = { book ->
                viewModel.toggleFavorite(book)
            },
            onItemClick = { book ->
                book.id?.let { id ->
                    val action =
                        CategoryFragmentDirections.actionCategoryFragmentToDetailsFragment(id)
                    findNavController().navigate(action)
                }
            },
            onCheckFavorite = { bookId ->
                viewModel.isBookFavorite(bookId)
            },
            lifecycleOwner = viewLifecycleOwner
        )
        binding.rvCategoriesPage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoriesPage.adapter = booksAdapter
    }

    // set adapter for categories
    private fun setupCategoryAdapter() {
        categoriesAdapter = CategoriesAdapter(emptyList()) { category ->
            viewModel.getBooksByCategory(category)
        }
        binding.rvCategoriesPage.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoriesPage.adapter = categoriesAdapter
        binding.rvCategoriesPage.setHasFixedSize(true)


    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.books.collectLatest { list ->
                booksAdapter.setData(list ?: emptyList())
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
            viewModel.categories.collectLatest { list ->
                categoriesAdapter = CategoriesAdapter(list) { category ->
                    viewModel.getBooksByCategory(category)
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