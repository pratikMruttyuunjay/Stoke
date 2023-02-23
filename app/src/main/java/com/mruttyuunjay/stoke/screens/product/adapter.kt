package com.mruttyuunjay.stoke.screens.product

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mruttyuunjay.stoke.R
import com.mruttyuunjay.stoke.databinding.SimpleTextDropDownBinding
import com.mruttyuunjay.stoke.dto.category.CategoryList
import com.mruttyuunjay.stoke.dto.category.CategoryListData

//class CustomArrayAdapter(context: Context, private val data: List<CategoryListData>) :
//    ArrayAdapter<CategoryListData>(context, R.layout.simple_text_drop_down, data) {
//
//    private val inflater = LayoutInflater.from(context)
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val binding: SimpleTextDropDownBinding
//        val view = convertView ?: inflater.inflate(R.layout.simple_text_drop_down, parent, false)
//        binding = SimpleTextDropDownBinding.bind(view)
//
//        val currentItem = data[position]
//        binding.text1.text = currentItem.title
//
//        return view
//    }
//}

class CustomArrayAdapter(
    context: Context,
    private val itemList: List<CategoryListData>
) : ArrayAdapter<CategoryListData>(context, 0, itemList) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            // inflate the layout and create a new view holder
            val binding = SimpleTextDropDownBinding.inflate(layoutInflater, parent, false)
            view = binding.root
            viewHolder = ViewHolder(binding)
            view.tag = viewHolder
        } else {
            // reuse the existing view holder
            viewHolder = view.tag as ViewHolder
        }
        Log.d("CustomArrayAdapter", "itemList size: ${itemList.size}")
        // bind the data
        val item = itemList[position].title
        viewHolder.bind(item)
        Log.d("CustomArrayAdapter", "getView called for position: $position")
        return view
    }

    // view holder class to hold references to views
    private class ViewHolder(val binding: SimpleTextDropDownBinding) {
        fun bind(item: String) {
            binding.text1.text = item
        }
    }
}


