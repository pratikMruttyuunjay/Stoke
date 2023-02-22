package com.mruttyuunjay.stoke.screens

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruttyuunjay.stoke.dto.AddQty
import com.mruttyuunjay.stoke.dto.MinusQty
import com.mruttyuunjay.stoke.repos.Rds
import com.mruttyuunjay.stoke.screens.batch.BatchEvents
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Vms @Inject constructor(
    private val repos: Rds
) :ViewModel() {

    private val _addQty = MutableLiveData<Resource<AddQty>>()
    val addQty = _addQty

    private val _minusQty = MutableLiveData<Resource<MinusQty>>()
    val minusQty = _minusQty


    fun onEvent(events: Events) {
        when (events) {
          is  Events.AddQty -> {
                viewModelScope.launch {
                    _addQty.value = Resource.Loading()
                    try {
                        val response = repos.postAddQty(
                            batch_id = events.batch_id,
                            qty = events.qty
                        )
                        Log.wtf("#PostProductList: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _addQty.value = getBody
                    } catch (e: Exception) {
                        _addQty.value = Resource.Error(message = e.message)
                    }
                }
            }
            is  Events.MinusQty -> {
                viewModelScope.launch {
                    _minusQty.value = Resource.Loading()
                    try {
                        val response = repos.postMinusQty(
                            batch_id = events.batch_id,
                            qty = events.qty
                        )
                        Log.wtf("#PostProductList: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _minusQty.value = getBody
                    } catch (e: Exception) {
                        _minusQty.value = Resource.Error(message = e.message)
                    }
                }
            }
        }
    }

}