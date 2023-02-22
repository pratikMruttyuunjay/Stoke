package com.mruttyuunjay.stoke.screens.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mruttyuunjay.stoke.dto.category.CategoryAdd
import com.mruttyuunjay.stoke.dto.category.CategoryList
import com.mruttyuunjay.stoke.dto.category.CategoryUpdate
import com.mruttyuunjay.stoke.repos.Rds
import com.mruttyuunjay.stoke.utils.Resource
import com.mruttyuunjay.stoke.utils.response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryVm @Inject constructor(
    private val repos: Rds,
) : ViewModel() {

    private var _categoryList = MutableLiveData<Resource<CategoryList>>()
    val categoryList = _categoryList

    private var _categoryUpdate = MutableLiveData<Resource<CategoryUpdate>>()
    val categoryUpdate = _categoryUpdate

    fun onEvent(events: CategoryEvents){

        when (events){
            CategoryEvents.CategoryList -> {
                viewModelScope.launch {
                    _categoryList.value = Resource.Loading()
                    try {
                        val response = repos.postCategoryList()
                        Log.wtf("#CategoryList: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _categoryList.value = getBody
                    } catch (e: Exception) {
                        _categoryList.value = Resource.Error(message = e.message)
                    }
                }
            }
//            is CategoryEvents.CategoryAdd -> {
//
//            }
            is CategoryEvents.CategoryUpdate -> {
                viewModelScope.launch {
                    _categoryUpdate.value = Resource.Loading()
                    try {
                        val response = repos.postCategoryUpdate(events.category_id,events.title)
                        Log.wtf("#CategoryUpdate: Body.data", response.body()?.data.toString())
                        val getBody = response(response)
                        _categoryUpdate.value = getBody
                    } catch (e: Exception) {
                        _categoryUpdate.value = Resource.Error(message = e.message)
                    }
                }
            }
        }

    }

}