package com.mruttyuunjay.stoke.dto.category

import com.mruttyuunjay.stoke.utils.ResponseModel

data class CategoryList(
    val data: ArrayList<CategoryListData>,
    override val message: String,
    override val status: String
): ResponseModel

data class CategoryListData(
    val created_at: String,
    val id: String,
    val status: String,
    val title: String,
    val total_product: String,
    val updated_at: String
)