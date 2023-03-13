package com.mruttyuunjay.stoke.dto.product

import com.mruttyuunjay.stoke.utils.ResponseModel

data class ProductList(
    val data: ArrayList<ProductListData>,
    override val message: String,
    override val status: String
): ResponseModel

data class ProductListData(
    val id: String,
    val title: String,
    val status: String,
    val created_at: String,
    val total_product: String,
    val updated_at: String
)