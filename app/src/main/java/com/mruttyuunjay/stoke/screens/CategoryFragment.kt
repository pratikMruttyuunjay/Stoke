package com.mruttyuunjay.stoke.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mruttyuunjay.stoke.databinding.FragmentCategoryBinding
import com.mruttyuunjay.stoke.utils.navigateToAdd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            navigateToAdd()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}