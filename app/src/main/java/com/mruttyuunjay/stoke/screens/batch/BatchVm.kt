package com.mruttyuunjay.stoke.screens.batch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruttyuunjay.stoke.dto.batch.BatchAdd
import com.mruttyuunjay.stoke.dto.batch.BatchList
import com.mruttyuunjay.stoke.dto.batch.BatchUpdate
import com.mruttyuunjay.stoke.repos.Rds
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatchVm @Inject constructor(
    private val repos: Rds,
) : ViewModel() {

    private val _batchList = MutableLiveData<Resource<BatchList>>()
    val batchList = _batchList

    private val _batchUpdate = MutableLiveData<Resource<BatchUpdate>>()
    val batchUpdate = _batchUpdate

    fun onEvent(events: BatchEvents) {
        when (events) {
            BatchEvents.BatchList -> {
                viewModelScope.launch {
                    _batchList.value = Resource.Loading()
                    try {
                        val response = repos.postBatchList()
                        Log.wtf("#PostProductList: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _batchList.value = getBody
                    } catch (e: Exception) {
                        _batchList.value = Resource.Error(message = e.message)
                    }
                }
            }
            is BatchEvents.BatchUpdate -> {
                viewModelScope.launch {
                    _batchUpdate.value = Resource.Loading()
                    try {
                        val response =
                            repos.postBatchUpdate(batch_id = events.batch_id, title = events.title)
                        Log.wtf("#PostProductList: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _batchUpdate.value = getBody
                    } catch (e: Exception) {
                        _batchUpdate.value = Resource.Error(message = e.message)
                    }
                }
            }
        }
    }

}