package com.mruttyuunjay.stoke.utils

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mruttyuunjay.stoke.R
import com.mruttyuunjay.stoke.screens.CategoryFragment
import com.mruttyuunjay.stoke.screens.ProductFragment
import com.mruttyuunjay.stoke.screens.batch.BatchFragment


fun popupTo(
    popUpTo: Int = -1,
    popUpInclusive: Boolean = false,
    singleTop: Boolean = true
): NavOptions {
    val builder = NavOptions.Builder()
    builder.setPopUpTo(popUpTo, popUpInclusive).setLaunchSingleTop(singleTop)
    return builder.build()
}

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String): T? {
    val value = getString(key) ?: return null
    return enumValues<T>().find { it.name == value }
}
inline fun <reified T : Enum<T>> Bundle.putEnum(key: String, value: T) {
    putString(key, value.name)
}

internal fun ProductFragment.navigateToAdd(
    productTitle: String? = null,
    productId: String? = null
) {
    val bundle = bundleOf(
        "productTitle" to productTitle,
        "productId" to productId,
        "from" to 1,
    )
    findNavController().navigate(
        R.id.action_ProductFragment_to_AddFragment,
        bundle,
        popupTo()
    )
}

internal fun CategoryFragment.navigateToAdd(
) {
    val bundle = bundleOf(
        "from" to 2
    )
    findNavController().navigate(
        R.id.action_CategoryFragment_to_AddFragment,
        bundle,
        popupTo()
    )
}

internal fun BatchFragment.navigateToAdd() {
    val bundle = bundleOf(
        "from" to 3
    )
    findNavController().navigate(
        R.id.action_BatchFragment_to_AddFragment,
        bundle,
        popupTo()
    )
}