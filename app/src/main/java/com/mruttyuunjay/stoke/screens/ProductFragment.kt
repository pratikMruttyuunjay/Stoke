package com.mruttyuunjay.stoke.screens

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mruttyuunjay.stoke.databinding.FragmentProductBinding
import com.mruttyuunjay.stoke.databinding.LayoutUpdateBinding
import com.mruttyuunjay.stoke.screens.product.ProductEvents
import com.mruttyuunjay.stoke.screens.product.ProductListingAdapter
import com.mruttyuunjay.stoke.screens.product.ProductVm
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.navigateToAdd
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

        binding.add.setOnClickListener {
            navigateToAdd()
        }
        binding.add.setOnLongClickListener {
            updateDialog(
                onSuccess = {

                },
                onDismiss = {

                }
            )
            return@setOnLongClickListener true
        }

        productAdapter = ProductListingAdapter({ onClick ->

//            navigateToProductAddOrUpdate(productTitle = onClick.title, productId = onClick.id)
        }) { onLongClick ->
            updateDialog(
                onSuccess = {

                },
                onDismiss = {

                }
            )
        }

        binding.rvProductList.adapter = productAdapter
        initFetch()
    }

    private fun initFetch() {

        if (vm.productList.value == null) vm.onEvent(ProductEvents.PostProductList)

        vm.productList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
//                        UiHelper.loadingDialogBuilder.dismissSafe()
                        productAdapter.setCommonData(it)
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

    private fun updateDialog(
        productId: String? = null,
        categoryId: String? = null,
        batchId: String? = null,
        onSuccess: () -> Unit,
        onDismiss: () -> Unit
    ) {

        val dialog = Dialog(requireContext())

        /*              For full screen dialog
        com.google.android.material.R.style.Theme_Material3_Light_BottomSheetDialog
        */

        val vb = LayoutUpdateBinding.inflate(LayoutInflater.from(context))

        vb.toolbarHeading.text = "Update"
        vb.addBtn.text = "Update"
        vb.back.setOnClickListener {
            onDismiss.invoke()
            dialog.dismiss()
        }

        dialog.setContentView(vb.root)

        // Set the dialog window attributes
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams

        dialog.setCancelable(true)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}