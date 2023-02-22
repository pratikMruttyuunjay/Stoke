package com.mruttyuunjay.stoke.dto

import com.mruttyuunjay.stoke.utils.ResponseModel

data class AddQty(
    val data: AddQtyData,
    override val message: String,
    override val status: String
):ResponseModel

data class AddQtyData(
    val created_at: String,
    val id: String,
    val product_id: String,
    val qty: String,
    val status: String,
    val title: String,
    val updated_at: String
)