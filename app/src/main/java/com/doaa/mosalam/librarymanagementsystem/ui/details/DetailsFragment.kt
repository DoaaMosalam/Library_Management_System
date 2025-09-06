package com.doaa.mosalam.librarymanagementsystem.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.common.BaseUserNameFragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentDetailsBinding
import com.doaa.mosalam.librarymanagementsystem.ui.details.viewModel.DetailsViewModel
import com.doaa.mosalam.librarymanagementsystem.utils.CommonClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseUserNameFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()


    override fun getLayoutResID(): Int = R.layout.fragment_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setUp user menu for username TextView
        setupUserMenu(binding.commonHeader.userName)

        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val bookId = args.bookId

        lifecycleScope.launch {
            viewModel.getBookDetails(bookId!!)
        }

        // init listeners
        initListener()

        // setup observers
        setUpObserve()

    }

    override fun navigateToProfile() {
        findNavController().navigate(R.id.action_detailsFragment_to_profileFragment)
    }

    override fun navigateToPayments() {
        TODO("Not yet implemented")
    }

    private fun initListener() {
        val commonClick = CommonClickListener { v ->
            when (v.id) {
                R.id.btn_myShelf -> v.findNavController()
                    .navigate(R.id.action_detailsFragment_to_mySelfFragment)

            }
        }
        binding.commonHeader.btnMyShelf.setOnClickListener(commonClick)
    }

    private fun setUpObserve() {
        lifecycleScope.launch {
            viewModel.loading.collectLatest { load ->
                binding.commonHeader.progressIndicator.visibility =
                    if (load) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let {
                    Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.bookDetails.collectLatest { volume ->
                volume?.let {
                    binding.tvBookTitle.text = it.volumeInfo?.title ?: "No Title"
                    binding.tvBookAuthor.text =
                        it.volumeInfo?.authors?.joinToString(", ") ?: "Unknown"
                    binding.tvDescription.text = it.volumeInfo?.description ?: "No Description"
                    binding.tvReleaseDate.text =
                        "Release Date: ${it.volumeInfo?.publishedDate ?: "-"}"

                    binding.bookRating.rating = it.volumeInfo?.averageRating?.toFloat() ?: 0f
                    binding.tvRatingCount.text =
                        "${it.volumeInfo?.averageRating ?: 0f} (${it.volumeInfo?.ratingsCount ?: 0} ratings)"

                    binding.tvPrice.text = it.saleInfo?.listPrice?.amount?.let { price ->
                        "$price ${it.saleInfo?.listPrice?.currencyCode ?: ""}"
                    } ?: "Price not available"

                    Glide.with(binding.ivBookCover.context)
                        .load(it.volumeInfo?.imageLinks?.thumbnail)
                        .placeholder(R.drawable.bg_placeholder)
                        .into(binding.ivBookCover)
                }
            }
        }
    }


}
