package com.doaa.mosalam.librarymanagementsystem.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.doaa.mosalam.librarymanagementsystem.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}