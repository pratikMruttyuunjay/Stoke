package com.mruttyuunjay.stoke.screens

sealed class Events{

    data class AddQty(
        val batch_id: String,
        val qty: String
    ): Events()

    data class MinusQty(
        val batch_id: String,
        val qty: String
    ): Events()

}