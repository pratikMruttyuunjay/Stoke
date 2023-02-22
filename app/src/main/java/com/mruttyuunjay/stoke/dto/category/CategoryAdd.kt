package com.mruttyuunjay.stoke.dto.category

import com.mruttyuunjay.stoke.utils.ResponseModel

data class CategoryAdd(
    val data: CategoryAddData,
    override val message: String,
    override val status: String
): ResponseModel

data class CategoryAddData(
    val categoryId: Int,
    val categoryTitle: String
)