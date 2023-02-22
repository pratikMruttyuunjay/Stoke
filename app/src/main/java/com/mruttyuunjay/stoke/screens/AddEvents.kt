package com.mruttyuunjay.stoke.screens

import com.mruttyuunjay.stoke.screens.batch.BatchEvents
import com.mruttyuunjay.stoke.screens.category.CategoryEvents
import com.mruttyuunjay.stoke.screens.product.ProductEvents

sealed class AddEvents {

    data class ProductAdd(
        val category_id: String,
        val title: String
    ) : AddEvents()

    data class CategoryAdd(
        val title: String
    ) : AddEvents()

    data class BatchAdd(
        val product_id: String,
        val title: String,
        val qty: String
    ) : AddEvents()

}