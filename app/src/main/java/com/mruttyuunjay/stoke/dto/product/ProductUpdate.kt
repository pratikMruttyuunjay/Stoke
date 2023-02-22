package com.mruttyuunjay.stoke.dto.product

import com.mruttyuunjay.stoke.utils.ResponseModel

data class ProductUpdate(
    val data: ProductUpdateData,
    override val message: String,
    override val status: String
): ResponseModel

data class ProductUpdateData(
    val category_id: String,
    val created_at: String,
    val id: String,
    val status: String,
    val title: String,
    val updated_at: String
)