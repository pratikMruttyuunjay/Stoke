package com.mruttyuunjay.stoke.screens.category

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mruttyuunjay.stoke.databinding.FragmentCategoryBinding
import com.mruttyuunjay.stoke.screens.product.ProductEvents
import com.mruttyuunjay.stoke.screens.product.ProductListingAdapter
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.UiHelper
import com.mruttyuunjay.stoke.utils.navigateToAdd
import com.mruttyuunjay.stoke.utils.navigateToProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val vm: CategoryVm by viewModels()
    private lateinit var categoryAdapter: CategoryListingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            navigateToAdd()
        }

        categoryAdapter = CategoryListingAdapter({ onClick ->
            navigateToProduct(onClick.id)
        }) { onLongClick ->
            UiHelper.updateDialog(
                context = requireActivity(),
                txt = onLongClick.title,
                from = UiHelper.updateFrom.Category,
                onUpdateClick = { title, dialog ->
                    updateCategory(categoryId = onLongClick.id, title = title, dialog = dialog)
                },
                onDismiss = {

                }
            )
        }
        binding.rvCategoryList.adapter = categoryAdapter
        initFetch()
    }

    private fun initFetch() {

//        if (vm.categoryList.value == null) vm.onEvent(CategoryEvents.CategoryList)
        vm.onEvent(CategoryEvents.CategoryList)

        vm.categoryList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.progress.visibility = View.GONE
                        categoryAdapter.setCommonData(it)
                    }
                }
                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateCategory(categoryId: String, title: String, dialog: Dialog) {
        vm.onEvent(
            CategoryEvents.CategoryUpdate(
                category_id = categoryId, title = title
            )
        )

        vm.categoryUpdate.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
//                        UiHelper.loadingDialogBuilder.dismissSafe()
                        dialog.dismiss()
//                        vm.categoryList.value = null
                        initFetch()
                    }
                }
                is Resource.Error -> {
//                    UiHelper.loadingDialogBuilder.dismissSafe()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
//                    UiHelper.loadingDialog(requireContext())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}