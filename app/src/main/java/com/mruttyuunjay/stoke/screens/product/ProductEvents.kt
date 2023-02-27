package com.mruttyuunjay.stoke.screens.product

sealed class ProductEvents {

    data class PostProductList(
        val category_id: String
    ) : ProductEvents()

    data class PostProductUpdate(
        val product_id: String,
        val title: String
    ) : ProductEvents()

//    data class PostProductAdd(
//        val category_id: String,
//        val title: String
//    ) : ProductEvents()

}