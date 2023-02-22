package com.mruttyuunjay.stoke.dto.batch

import com.mruttyuunjay.stoke.utils.ResponseModel

data class BatchList(
    val data: ArrayList<BatchListData>,
    override val message: String,
    override val status: String
): ResponseModel

data class BatchListData(
    val created_at: String,
    val id: String,
    val product_id: String,
    val qty: String,
    val status: String,
    val title: String,
    val updated_at: String
)