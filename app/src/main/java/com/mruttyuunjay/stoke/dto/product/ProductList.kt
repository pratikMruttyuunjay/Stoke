package com.mruttyuunjay.stoke.dto.product

import com.mruttyuunjay.stoke.utils.ResponseModel

data class ProductList(
    val data: ArrayList<ProductListData>,
    override val message: String,
    override val status: String
): ResponseModel

data class ProductListData(
    val created_at: String,
    val id: String,
    val status: String,
    val title: String,
    val total_product: String,
    val updated_at: String
)