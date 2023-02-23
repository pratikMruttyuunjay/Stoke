package com.mruttyuunjay.stoke.screens.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruttyuunjay.stoke.dto.product.ProductAdd
import com.mruttyuunjay.stoke.dto.product.ProductList
import com.mruttyuunjay.stoke.dto.product.ProductUpdate
import com.mruttyuunjay.stoke.repos.Rds
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductVm @Inject constructor(
    private val repos: Rds,
) : ViewModel() {

    private var _productList = MutableLiveData<Resource<ProductList>>()
    val productList = _productList

    private var _productUpdate = MutableLiveData<Resource<ProductUpdate>>()
    val productUpdate = _productUpdate

    fun onEvent(event: ProductEvents) {

        when (event) {
            ProductEvents.PostProductList -> {
                viewModelScope.launch {
                    _productList.value = Resource.Loading()
                    try {
                        val response = repos.postProductList()
                        Log.wtf("#PostProductList: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _productList.value = getBody
                    } catch (e: Exception) {
                        _productList.value = Resource.Error(message = e.message)
                    }
                }
            }

//            is ProductEvents.PostProductAdd -> {
//
//            }

            is ProductEvents.PostProductUpdate -> {
                viewModelScope.launch {
                    _productUpdate.value = Resource.Loading()
                    try {
                        val response = repos.postProductUpdate(
                            product_id = event.product_id,
                            title = event.title
                        )
                        Log.wtf("#PostProductUpdate: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _productUpdate.value = getBody
                    } catch (e: Exception) {
                        _productUpdate.value = Resource.Error(message = e.message)
                    }
                }
            }

        }
    }
}