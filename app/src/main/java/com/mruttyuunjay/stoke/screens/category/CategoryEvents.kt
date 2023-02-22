package com.mruttyuunjay.stoke.screens.category

sealed class CategoryEvents {

    object CategoryList : CategoryEvents()

//    data class CategoryAdd(
//        val title: String
//    ) : CategoryEvents()

    data class CategoryUpdate(
        val category_id: String,
        val title: String
    ) : CategoryEvents()

}
