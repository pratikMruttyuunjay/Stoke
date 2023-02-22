package com.mruttyuunjay.stoke.dto.batch

import com.mruttyuunjay.stoke.utils.ResponseModel

data class BatchAdd(
    val data: BatchAddData,
    override val message: String,
    override val status: String
): ResponseModel

data class BatchAddData(
    val batchId: Int,
    val batchQty: String,
    val batchTitle: String
)