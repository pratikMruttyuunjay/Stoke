package com.mruttyuunjay.stoke.screens.batch

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.mruttyuunjay.stoke.databinding.FragmentBatchBinding
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.UiHelper
import com.mruttyuunjay.stoke.utils.navigateToAdd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BatchFragment : Fragment() {

    private var _binding: FragmentBatchBinding? = null
    private val binding get() = _binding!!
    private val vm : BatchVm by viewModels()
    private lateinit var batchAdapter: BatchListingAdapter

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

        batchAdapter = BatchListingAdapter(
            fragment = this,
            onClick = {},
            onLongClick = { onLongClick->
                UiHelper.updateDialog(
                    context = requireActivity(),
                    from = UiHelper.updateFrom.Batch,
                    onUpdateClick = {title,dialog ->
                        updateBatch(batchId = onLongClick.id, title = title, dialog = dialog)
                    },
                    onDismiss = {

                    },
                )
            }
        )
        binding.rvBatchList.adapter = batchAdapter
        initFetch()
    }

    private fun initFetch() {

        if (vm.batchList.value == null) vm.onEvent(BatchEvents.BatchList)

        vm.batchList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
//                        UiHelper.loadingDialogBuilder.dismissSafe()
                        batchAdapter.setCommonData(it)
                        Log.d("batchList",response.message.toString())
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

     fun addQty (batchId: String,qty:String,progress:CircularProgressIndicator){

         Log.d("addQty", qty)
       vm.onEvent(BatchEvents.AddQty(batch_id = batchId, qty = qty))

        vm.addQty.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        progress.visibility = View.GONE
                        vm.batchList.value = null
                        initFetch()
                        Log.d("addQty",response.data.data.toString())
                        Log.d("addQty",response.message.toString())
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                    progress.visibility = View.VISIBLE
                }
            }
        }
    }

    fun minusQty (batchId: String,qty:String){

         vm.onEvent(BatchEvents.MinusQty(batch_id = batchId, qty = qty))

        vm.minusQty.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        vm.batchList.value = null
                        initFetch()
                        Log.d("addQty",response.data.data.toString())
                        Log.d("addQty",response.message.toString())
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun updateBatch(batchId:String,title:String,dialog: Dialog){
        if (vm.batchUpdate.value == null) vm.onEvent(BatchEvents.BatchUpdate(batch_id = batchId, title = title))

        vm.batchUpdate.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
//                        UiHelper.loadingDialogBuilder.dismissSafe()
                        dialog.dismiss()
                        vm.batchList.value = null
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