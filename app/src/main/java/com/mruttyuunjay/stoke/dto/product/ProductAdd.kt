package com.mruttyuunjay.stoke.dto.product

import com.mruttyuunjay.stoke.utils.ResponseModel

data class ProductAdd(
    val data: ProductAddData,
    override val message: String,
    override val status: String
): ResponseModel

data class ProductAddData(
    val productId: Int,
    val productTitle: String
)