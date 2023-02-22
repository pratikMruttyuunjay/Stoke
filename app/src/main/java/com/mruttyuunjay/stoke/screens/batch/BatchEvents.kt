package com.mruttyuunjay.stoke.screens.batch

sealed class BatchEvents {

    object BatchList : BatchEvents()

//    data class BatchAdd(
//        val product_id: String,
//        val title: String,
//        val qty: String
//    ) : BatchEvents()

    data class BatchUpdate(
        val batch_id: String,
        val title: String
    ) : BatchEvents()

}
