package com.mruttyuunjay.stoke.screens

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mruttyuunjay.stoke.R
import com.mruttyuunjay.stoke.databinding.FragmentAddBinding
import com.mruttyuunjay.stoke.dto.category.CategoryListData
import com.mruttyuunjay.stoke.dto.product.ProductList
import com.mruttyuunjay.stoke.dto.product.ProductListData
import com.mruttyuunjay.stoke.screens.category.CategoryEvents
import com.mruttyuunjay.stoke.screens.category.CategoryVm
import com.mruttyuunjay.stoke.screens.product.*
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.getEnum
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val vm: AddVm by viewModels()
    private val cVm: CategoryVm by viewModels()
    private val pVm: ProductVm by viewModels()
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aPId = arguments?.getString("productId") ?: ""
        val aCId = arguments?.getString("categoryId") ?: ""
        val aFrom = arguments?.getInt("from")        /*  1 = Product , 2 = Category , 3 = Batch  */

        Log.wtf("aPId", aPId)
        Log.wtf("aCId", aCId)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.apply {
            when (aFrom) {
                1 -> {
//                    initCategory()
                    "Add Product".apply {
                        toolbarHeading.text = this
                        addBtn.text = this
                    }
                }
                2 -> {
                    "Add Category".apply {
                        addBtn.text = this
                        toolbarHeading.text = this
                    }
                }
                3 -> {
//                    initProduct()
                    "Add Batch".apply {
                        addBtn.text = this
                        toolbarHeading.text = this
                    }
                    outlineEditTextQty.visibility = View.VISIBLE
                }
            }

            addBtn.apply {
                setOnClickListener {
                    initAdd(
                        product_id = aPId,
                        category_id = aCId,
                        title = binding.titleValue.text.toString(),
                        qty = binding.quantityValue.text.toString()
                    )
                }
            }
        }
    }

//    private fun initProduct() {
//
//        if (pVm.productList.value == null) pVm.onEvent(ProductEvents.PostProductList())
//
//        pVm.productList.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is Resource.Success -> {
//                    Log.d("ProductList", "Product Fetched")
//                    if (response.data != null) {
////                        productList = response.data.data
//                    } else {
//                        Toast.makeText(requireContext(), "Product Data is null", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }
//                is Resource.Loading -> {
//
//                }
//                is Resource.Error -> {
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }
//
//    private fun initCategory() {
//
//        if (cVm.categoryList.value == null) cVm.onEvent(CategoryEvents.CategoryList)
//
//        cVm.categoryList.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is Resource.Success -> {
//                    Log.d("Category List", "Category Fetched")
//                    if (response.data != null) {
////                        categoryList = response.data.data
//                    } else {
//                        Toast.makeText(requireContext(), "Category Data is null", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }
//                is Resource.Loading -> {
//
//                }
//                is Resource.Error -> {
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }

    private fun initAdd(
        product_id: String? = null,
        category_id: String? = null,
        title: String,
        qty: String
    ) {

        if (!category_id.isNullOrEmpty()) {
            vm.onEvent(AddEvents.ProductAdd(category_id = category_id, title = title))
        } else if (!product_id.isNullOrEmpty()) {
            if (qty.isNotEmpty()) {
                vm.onEvent(AddEvents.BatchAdd(title = title, product_id = product_id, qty = qty))
            } else {
                Toast.makeText(requireContext(), "You need to add Quantity", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            vm.onEvent(AddEvents.CategoryAdd(title = title))
        }

        vm.productAdd.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    Log.wtf("ProductAdd", response.message.toString())
                }
                is Resource.Loading -> {

                }
            }
        }
        vm.categoryAdd.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    Log.wtf("CategoryAdd", response.message.toString())
                }
                is Resource.Loading -> {

                }
            }
        }
        vm.batchAdd.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    Log.wtf("BatchAdd", response.message.toString())
                }
                is Resource.Loading -> {

                }
            }
        }

    }
}