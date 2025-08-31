package com.doaa.mosalam.librarymanagementsystem.ui.home


import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.BooksAdapter
import com.doaa.mosalam.librarymanagementsystem.adapter.CategoriesAdapter
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentHomeBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel
import com.doaa.mosalam.librarymanagementsystem.ui.search.SearchViewModel
import com.doaa.mosalam.librarymanagementsystem.ui.search.debounceSearch
import com.doaa.mosalam.librarymanagementsystem.utils.CommonClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BasicFragment<FragmentHomeBinding, HomeViewModel>() {
    private val vm: HomeViewModel by viewModels()

    override val viewModel: HomeViewModel
        get() = vm


    override fun getLayoutResID(): Int = R.layout.fragment_home
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var booksadapter: BooksAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// set adapters for  Trending Books
        setupAdapter()
        // set adapter for categories
        setupCategoryAdapter()

        setupObservers()
        initListener()
        vm.getTrendingBooks()

        val searchEdit = binding.commonHeader.searchInput
        setupSearchEdit(searchEdit)
    }

    // Observers to handle data changes and update UI
    private fun setupObservers() {
        lifecycleScope.launch {
            vm.books.collectLatest { list ->
                booksadapter.setData(list ?: emptyList())
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
                binding.rvCategories.adapter = categoriesAdapter

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.booksByCategory.collect { books ->

                    booksadapter.setData(books)
                    binding.rvCategoriesBooks.adapter = booksadapter
                }
            }
        }

        lifecycleScope.launch {
            viewModel.favoriteBooks.collect { favorites ->
                booksadapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            searchViewModel.searchResults.collectLatest { list ->
                if (list.isNotEmpty()) {
                    // show search results
                    booksadapter.setData(list)
                    binding.rvTrendingBooks.adapter = booksadapter
                } else {
                    // show trending books if search results are empty
                    vm.books.collectLatest { trending ->
                        booksadapter.setData(trending ?: emptyList())
                        binding.rvTrendingBooks.adapter = booksadapter
                    }
                }
            }
        }
    }

    // setup search with debounce
//    editText.debounceSearch(viewLifecycleOwner) { query ->
//        myViewModel.searchBooks(query)
//    }
    private fun setupSearchEdit(editText: EditText) {

        val searchEdit: EditText = binding.commonHeader.searchInput

        searchEdit.debounceSearch(viewLifecycleOwner) { query ->
            if (query.isEmpty()) {
                vm.getTrendingBooks()       // Show Trending Books when search is cleared
            } else {
                searchViewModel.searchBooks(query) // Call Search API
            }
        }
    }

// set adapters for  Trending Books

    private fun setupAdapter() {
        booksadapter = BooksAdapter(
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
        binding.rvTrendingBooks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendingBooks.adapter = booksadapter
    }

    // set adapter for categories
    private fun setupCategoryAdapter() {
        categoriesAdapter = CategoriesAdapter(emptyList()) { category ->
            vm.getBooksByCategory(category)
        }
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.setHasFixedSize(true)


    }

    // initialize click listeners
    private fun initListener() {
        val commonClick = CommonClickListener { v ->
            when (v.id) {
                R.id.viewAllTrending -> v.findNavController()
                    .navigate(R.id.action_homeFragment_to_trendingFragment)

                R.id.viewAllCategories -> v.findNavController()
                    .navigate(R.id.action_homeFragment_to_categoryFragment)
            }
        }
        binding.viewAllTrending.setOnClickListener(commonClick)
        binding.viewAllCategories.setOnClickListener(commonClick)
    }
}