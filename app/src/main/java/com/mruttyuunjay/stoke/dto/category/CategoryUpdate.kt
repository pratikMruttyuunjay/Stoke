package com.mruttyuunjay.stoke.dto.category

import com.mruttyuunjay.stoke.utils.ResponseModel

data class CategoryUpdate(
    val data: CategoryUpdateData,
    override val message: String,
    override val status: String
): ResponseModel

data class CategoryUpdateData(
    val created_at: String,
    val id: String,
    val status: String,
    val title: String,
    val updated_at: String
)