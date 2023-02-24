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
import androidx.navigation.fragment.findNavController
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
        val aPId = arguments?.getString("productId") ?: ""

        binding.add.setOnClickListener {
            navigateToAdd(aPId)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        batchAdapter = BatchListingAdapter(
            onClick = {},
            onLongClick = { onLongClick->
                UiHelper.updateDialog(
                    context = requireActivity(),
                    from = UiHelper.updateFrom.Batch,
                    txt=onLongClick.title,
                    onUpdateClick = {title,dialog ->
                        updateBatch(batchId = onLongClick.id, title = title, dialog = dialog)
                    },
                    onDismiss = {

                    },
                )
            },
            addQty = { id,defaultQty ->
                UiHelper.qtyDialog(requireContext(),UiHelper.Qty.Inc,defaultQty.toString(),
                onUpdateClick = {
                    addQty(batchId = id, qty = defaultQty.plus(it))
                },
                onDismiss = {

                })

            },
            minusQty = {id,defaultQty ->
                UiHelper.qtyDialog(requireContext(),UiHelper.Qty.Dec,defaultQty.toString(),
                    onUpdateClick = {
                        minusQty(batchId = id,qty=defaultQty.minus(it))
                    },
                    onDismiss = {

                    })
            }
        )
        binding.rvBatchList.adapter = batchAdapter
        initFetch()
    }

    private fun initFetch() {

      vm.onEvent(BatchEvents.BatchList)

        vm.batchList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.progress.visibility = View.GONE
                        batchAdapter.setCommonData(it)
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

     private fun addQty (batchId: String,qty:Int){

         Log.d("addQty", qty.toString())
       vm.onEvent(BatchEvents.AddQty(batch_id = batchId, qty = qty.toString()))

        vm.addQty.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        vm.batchList.value = null
                        initFetch()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    private fun minusQty (batchId: String, qty:Int){
        Log.d("minusQty", qty.toString())
         vm.onEvent(BatchEvents.MinusQty(batch_id = batchId, qty = qty.toString()))

        vm.minusQty.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        vm.batchList.value = null
                        initFetch()
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
       vm.onEvent(BatchEvents.BatchUpdate(batch_id = batchId, title = title))

        vm.batchUpdate.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        dialog.dismiss()
                        vm.batchList.value = null
                        initFetch()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}