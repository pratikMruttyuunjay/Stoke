package com.mruttyuunjay.stoke.screens.product

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mruttyuunjay.stoke.databinding.FragmentProductBinding
import com.mruttyuunjay.stoke.databinding.LayoutUpdateBinding
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.UiHelper
import com.mruttyuunjay.stoke.utils.navigateToAdd
import com.mruttyuunjay.stoke.utils.navigateToBatch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val vm: ProductVm by viewModels()
    private lateinit var productAdapter: ProductListingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aCId = arguments?.getString("categoryId") ?: ""

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.add.setOnClickListener {
            navigateToAdd(aCId)
        }

        productAdapter = ProductListingAdapter({ onClick ->
            navigateToBatch(onClick.id)
        }) { onLongClick ->
            UiHelper.updateDialog(
                context = requireActivity(),
                from = UiHelper.updateFrom.Product,
                txt = onLongClick.title,
                onUpdateClick = { title, dialog ->
                    updateProduct(productId = onLongClick.id, title = title, dialog = dialog)
                },
                onDismiss = {

                }
            )
        }

        binding.rvProductList.adapter = productAdapter
        initFetch()
    }

    private fun initFetch() {

//        if (vm.productList.value == null) vm.onEvent(ProductEvents.PostProductList)
        vm.onEvent(ProductEvents.PostProductList)

        vm.productList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.progress.visibility = View.GONE
                        productAdapter.setCommonData(it)
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

    private fun updateProduct(productId: String, title: String, dialog: Dialog) {
       vm.onEvent(
            ProductEvents.PostProductUpdate(
                product_id = productId,
                title = title
            )
        )

        vm.productUpdate.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
//                        UiHelper.loadingDialogBuilder.dismissSafe()
                        dialog.dismiss()
                        vm.productList.value = null
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