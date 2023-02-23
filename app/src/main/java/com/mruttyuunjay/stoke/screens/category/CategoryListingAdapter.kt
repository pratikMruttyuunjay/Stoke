package com.mruttyuunjay.stoke.screens.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mruttyuunjay.stoke.databinding.ItemRvProductBinding
import com.mruttyuunjay.stoke.dto.category.CategoryList
import com.mruttyuunjay.stoke.dto.category.CategoryListData
import com.mruttyuunjay.stoke.utils.CommonDiff


class CategoryListingAdapter(
    private val onClick: (CategoryListData) -> Unit,
    private val onLongClick: (CategoryListData) -> Unit,
) : RecyclerView.Adapter<CategoryListingAdapter.ViewHolder>() {

    var mList: ArrayList<CategoryListData> = ArrayList()

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

    fun setCommonData(newData: CategoryList) {

        val movieDiffUtil = CommonDiff(mList, newData.data)

        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        mList = newData.data

        diffUtilResult.dispatchUpdatesTo(this)
    }

}

