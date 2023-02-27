package com.mruttyuunjay.stoke.screens.batch

sealed class BatchEvents {

    data class BatchList(
        val product_id: String
    ) : BatchEvents()

    data class BatchUpdate(
        val batch_id: String,
        val title: String
    ) : BatchEvents()

    data class AddQty(
        val batch_id: String,
        val qty: String
    ) : BatchEvents()

    data class MinusQty(
        val batch_id: String,
        val qty: String
    ) : BatchEvents()

}
