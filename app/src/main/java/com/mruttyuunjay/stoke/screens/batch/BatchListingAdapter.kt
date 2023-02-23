package com.mruttyuunjay.stoke.screens.batch

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mruttyuunjay.stoke.databinding.ItemRvBatchBinding
import com.mruttyuunjay.stoke.databinding.ItemRvProductBinding
import com.mruttyuunjay.stoke.dto.batch.BatchList
import com.mruttyuunjay.stoke.dto.batch.BatchListData
import com.mruttyuunjay.stoke.utils.CommonDiff


class BatchListingAdapter(
    private val fragment: BatchFragment,
    private val onClick: (BatchListData) -> Unit,
    private val onLongClick: (BatchListData) -> Unit
) : RecyclerView.Adapter<BatchListingAdapter.ViewHolder>() {

    var mList: ArrayList<BatchListData> = ArrayList()

    inner class ViewHolder(val binding: ItemRvBatchBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRvBatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
//            holder.binding.title.gravity = Gravity.START
            with(mList[position]) {
                binding.qty.text = this.qty
                binding.title.text = this.title
            }
        }

        val listWithPosition = mList[position]

        holder.binding.root.setOnClickListener {
//            onClick.invoke(listWithPosition)
        }

        val qty = listWithPosition.qty.toInt().inc()

        holder.binding.addQty.setOnClickListener {
//            holder.binding.progress.visibility = View.VISIBLE
            fragment.addQty(listWithPosition.id, qty = qty.toString(), progress = holder.binding.progress)
//            holder.binding.qty.text = listWithPosition.qty
            Log.wtf("qty",qty.toString())
        }

        holder.binding.minQty.setOnClickListener {
            fragment.minusQty(listWithPosition.id, qty = qty.dec().toString())
        }

        holder.binding.root.setOnLongClickListener {
            onLongClick.invoke(listWithPosition)
            return@setOnLongClickListener true
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCommonData(newData: BatchList) {

        val movieDiffUtil = CommonDiff(mList, newData.data)

        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        mList = newData.data

        diffUtilResult.dispatchUpdatesTo(this)
    }

}

