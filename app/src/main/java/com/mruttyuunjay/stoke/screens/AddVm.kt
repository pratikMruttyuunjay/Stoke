package com.mruttyuunjay.stoke.screens

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruttyuunjay.stoke.dto.batch.BatchAdd
import com.mruttyuunjay.stoke.dto.category.CategoryAdd
import com.mruttyuunjay.stoke.dto.product.ProductAdd
import com.mruttyuunjay.stoke.repos.Rds
import com.mruttyuunjay.stoke.screens.product.ProductEvents
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddVm @Inject constructor(
    private val repos: Rds,
) : ViewModel() {

    private var _productAdd = MutableLiveData<Resource<ProductAdd>>()
    val productAdd = _productAdd

    private var _categoryAdd = MutableLiveData<Resource<CategoryAdd>>()
    val categoryAdd = _categoryAdd

    private val _batchAdd = MutableLiveData<Resource<BatchAdd>>()
    val batchAdd = _batchAdd

    fun onEvent(events: AddEvents) {

        when (events) {
            is AddEvents.ProductAdd -> {
                viewModelScope.launch {
                    _productAdd.value = Resource.Loading()
                    try {
                        val response = repos.postProductAdd(
                            category_id = events.category_id,
                            title = events.title
                        )
                        Log.wtf("#ProductAdd: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _productAdd.value = getBody
                    } catch (e: Exception) {
                        _productAdd.value = Resource.Error(message = e.message)
                    }
                }
            }
            is AddEvents.CategoryAdd -> {
                viewModelScope.launch {
                    _categoryAdd.value = Resource.Loading()
                    try {
                        val response = repos.postCategoryAdd(events.title)
                        Log.wtf("#CategoryAdd: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _categoryAdd.value = getBody
                    } catch (e: Exception) {
                        _categoryAdd.value = Resource.Error(message = e.message)
                    }
                }
            }
            is AddEvents.BatchAdd -> {
                viewModelScope.launch {
                    _batchAdd.value = Resource.Loading()
                    try {
                        val response = repos.postBatchAdd(
                            product_id = events.product_id,
                            title = events.title,
                            qty = events.qty
                        )
                        Log.wtf("#BatchAdd: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _batchAdd.value = getBody
                    } catch (e: Exception) {
                        _batchAdd.value = Resource.Error(message = e.message)
                    }
                }
            }
        }
    }

}