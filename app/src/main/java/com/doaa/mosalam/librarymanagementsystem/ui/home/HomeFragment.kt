package com.doaa.mosalam.librarymanagementsystem.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.adapter.BooksAdapter
import com.doaa.mosalam.librarymanagementsystem.common.BasicFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentHomeBinding
import com.doaa.mosalam.librarymanagementsystem.ui.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue
@AndroidEntryPoint
class HomeFragment : BasicFragment<FragmentHomeBinding, HomeViewModel>() {
    private val vm: HomeViewModel by viewModels()

    override val viewModel: HomeViewModel
        get() = vm


    override fun getLayoutResID(): Int = R.layout.fragment_home

    private lateinit var adapter: BooksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewAllTrending.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_trendingFragment)
        }

        binding.viewAllCategories.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        setupAdapter()
        setupObservers()
        setupClickListeners()

        vm.getTrendingBooks()
    }
    private fun setupAdapter() {
        adapter = BooksAdapter(
            onRentClick = { book ->
                // TODO: handle rent click
            },
            onFavClick = { book ->
                // TODO: handle favorite click
            },
            onItemClick = { book ->
                // مثال: الانتقال لصفحة تفاصيل الكتاب
//                val action = HomeFragmentDirections.actionHomeFragmentToBookDetailsFragment(book.id ?: "")
//                findNavController().navigate(action)
            }
        )
        binding.rvTrendingBooks.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendingBooks.adapter = adapter
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
                    // عرض رسالة خطأ
                    // Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.viewAllTrending.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_trendingFragment)
        }

        binding.viewAllCategories.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
    }
}