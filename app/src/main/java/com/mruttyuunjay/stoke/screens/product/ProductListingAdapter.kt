package com.mruttyuunjay.stoke.screens.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mruttyuunjay.stoke.databinding.ItemRvProductBinding
import com.mruttyuunjay.stoke.dto.product.ProductList
import com.mruttyuunjay.stoke.dto.product.ProductListData
import com.mruttyuunjay.stoke.screens.ProductFragment
import com.mruttyuunjay.stoke.utils.CommonDiff


class ProductListingAdapter(
    private val onClick: (ProductListData) -> Unit,
    private val onLongClick: (ProductListData) -> Unit,
) : RecyclerView.Adapter<ProductListingAdapter.ViewHolder>() {

    var mList: ArrayList<ProductListData> = ArrayList()

    inner class ViewHolder(val binding: ItemRvProductBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRvProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(mList[position]) {
                binding.title.text = this.title
            }
        }


        val listWithPosition = mList[position]
        holder.binding.root.setOnClickListener {
            onClick.invoke(listWithPosition)
        }
        holder.binding.root.setOnLongClickListener {
            onLongClick.invoke(listWithPosition)
            return@setOnLongClickListener true
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCommonData(newData: ProductList) {

        val movieDiffUtil = CommonDiff(mList, newData.data)

        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        mList = newData.data

        diffUtilResult.dispatchUpdatesTo(this)
    }

}

