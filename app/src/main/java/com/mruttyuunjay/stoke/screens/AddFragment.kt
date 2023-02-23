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
    private lateinit var category :String
    private var categoryList : ArrayList<CategoryListData> = arrayListOf()
    private var productList : ArrayList<ProductListData> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aPTitle = arguments?.getString("productTitle") ?: ""
        val aPId = arguments?.getString("productId") ?: ""
        val aFrom = arguments?.getInt("from")        /*  1 = Product , 2 = Category , 3 = Batch  */

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.apply {

            when(aFrom){
               1-> {
                   initCategory()
                   outLineEditTextCategory.visibility = View.VISIBLE
                   addBtn.text = "Add Product"
               }
                2 -> {
                    addBtn.text = "Add Category"
                }
                3 -> {
                    initProduct()
                    addBtn.text = "Add Batch"
                    outlineEditTextQty.visibility = View.VISIBLE
                    outLineEditTextProduct.visibility = View.VISIBLE
                }
            }

            addBtn.apply {
                setOnClickListener {
                    initAdd(
                        product_id = aPId,
                        category_id = binding.categoryValue.text.toString(),
                        title = binding.titleValue.text.toString()
                    )
                }
            }
            titleValue.setText(aPTitle)

            categoryValue.keyListener = null
            productValue.keyListener = null

//            productValue.apply {
//
//                ArrayAdapter(
//                    requireContext(),
//                    R.layout.simple_text_drop_down,
//                    productList /* resources.getStringArray(R.array.PerList)*/
//                ).also {
//                    this.setAdapter(it)
//                    this.setDropDownBackgroundResource(R.color.white)
//
//                    onItemClickListener =
//                        AdapterView.OnItemClickListener { parent, _, position, _ ->
//                            val selected = parent.adapter.getItem(position).toString()
//                            category = selected
//                        }
//                }
//
//            }

//            val adapterr = CustomArrayAdapter(requireContext(),categoryList)
            val list = categoryList.map { it.title }.toList()
            categoryValue.apply {
                ArrayAdapter(
                    requireContext(),
                    R.layout.simple_text_drop_down,
                    list /*resources.getStringArray(R.array.PerList)*/
                ).also {
                    this.setAdapter(it)
                    this.setDropDownBackgroundResource(R.color.white)

                    onItemClickListener =
                        AdapterView.OnItemClickListener { parent, _, position, _ ->
                            val selected = parent.adapter.getItem(position).toString()
                            category = selected
                        }
                }

            }
        }
    }

    private fun initProduct() {

        if (pVm.productList.value == null) pVm.onEvent(ProductEvents.PostProductList)

        pVm.productList.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    Log.d("ProductList","Product Fetched")
                    if (response.data != null){
                        productList = response.data.data
                    }else {
                        Toast.makeText(requireContext(),"Product Data is null",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initCategory() {

        if (cVm.categoryList.value == null) cVm.onEvent(CategoryEvents.CategoryList)

        cVm.categoryList.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    Log.d("Category List","Category Fetched")
                    if (response.data != null){
                        categoryList = response.data.data
                    }else {
                        Toast.makeText(requireContext(),"Category Data is null",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),response.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initAdd(
        product_id: String? = null,
        category_id: String? = null,
        title: String,
        qty: String? = null
    ) {

        if (!category_id.isNullOrEmpty()) {
            vm.onEvent(AddEvents.ProductAdd(category_id = category_id, title = title))
        } else if (!product_id.isNullOrEmpty() && !qty.isNullOrEmpty()){
            vm.onEvent(AddEvents.BatchAdd(title = title, product_id = product_id, qty = qty))
        } else {
            vm.onEvent(AddEvents.CategoryAdd(title = title))
        }

        vm.productAdd.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {

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

                }
                is Resource.Error -> {
                    Log.wtf("BatchAdd", response.message.toString())
                }
                is Resource.Loading -> {

                }
            }
        }

    }

    private fun openCategoryPicker(
        activity: FragmentActivity,
        title: String? = null,
        onSuccess: (String) -> Unit,
        onDismiss: () -> Unit
    ) {

        val dialog = Dialog(requireContext())
//        val vb = LayoutQuanBinding
//            .inflate(LayoutInflater.from(context))

//        vb.add.setOnClickListener {
//            qty.invoke(vb.quanValue.text.toString().toInt())
//
////            Snackbar.make(context,vb.root,"Product Added",Snackbar.ANIMATION_MODE_FADE).show()
//
//            Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//            onSuccess.invoke()
//        }
//        vb.btnClose.setOnClickListener {
//            onDismiss.invoke()
//            dialog.dismiss()
//        }

    }
}