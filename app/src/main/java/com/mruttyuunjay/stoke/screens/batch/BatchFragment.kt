package com.mruttyuunjay.stoke.screens.batch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mruttyuunjay.stoke.R
import com.mruttyuunjay.stoke.databinding.FragmentBatchBinding
import com.mruttyuunjay.stoke.databinding.FragmentCategoryBinding
import com.mruttyuunjay.stoke.utils.navigateToAdd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BatchFragment : Fragment() {

    private var _binding: FragmentBatchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.add.setOnClickListener {
            navigateToAdd()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}