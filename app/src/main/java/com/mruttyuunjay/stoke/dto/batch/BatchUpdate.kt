package com.mruttyuunjay.stoke.dto.batch

import com.mruttyuunjay.stoke.utils.ResponseModel

data class BatchUpdate(
    val data: BatchUpdateData,
    override val message: String,
    override val status: String
): ResponseModel

data class BatchUpdateData(
    val created_at: String,
    val id: String,
    val product_id: String,
    val qty: String,
    val status: String,
    val title: String,
    val updated_at: String
)