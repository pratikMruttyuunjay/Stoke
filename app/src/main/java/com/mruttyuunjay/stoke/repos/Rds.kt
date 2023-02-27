package com.mruttyuunjay.stoke.repos

import android.content.Context
import com.mruttyuunjay.stoke.di.Ends
import com.mruttyuunjay.stoke.dto.AddQty
import com.mruttyuunjay.stoke.dto.MinusQty
import com.mruttyuunjay.stoke.dto.batch.BatchAdd
import com.mruttyuunjay.stoke.dto.batch.BatchList
import com.mruttyuunjay.stoke.dto.batch.BatchUpdate
import com.mruttyuunjay.stoke.dto.category.CategoryAdd
import com.mruttyuunjay.stoke.dto.category.CategoryList
import com.mruttyuunjay.stoke.dto.category.CategoryUpdate
import com.mruttyuunjay.stoke.dto.product.ProductAdd
import com.mruttyuunjay.stoke.dto.product.ProductList
import com.mruttyuunjay.stoke.dto.product.ProductUpdate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class Rds @Inject constructor(
    private val ends: Ends
) {

    suspend fun postProductList(category_id:String): Response<ProductList> {
        return withContext(Dispatchers.IO) { ends.postProductList("productList", status = "1", category_id = category_id) }
    }

    suspend fun postProductAdd(category_id: String, title: String): Response<ProductAdd> {
        return withContext(Dispatchers.IO) { ends.postProductAdd("productAdd", category_id, title) }
    }

    suspend fun postProductUpdate(product_id: String, title: String): Response<ProductUpdate> {
        return withContext(Dispatchers.IO) {
            ends.postProductUpdate(
                "productUpdate",
                product_id = product_id,
                title = title,
                status = "1"
            )
        }
    }


    suspend fun postCategoryList(): Response<CategoryList> {
        return withContext(Dispatchers.IO) { ends.postCategoryList("categoryList", status = "1") }
    }

    suspend fun postCategoryAdd(title: String): Response<CategoryAdd> {
        return withContext(Dispatchers.IO) { ends.postCategoryAdd("categoryAdd", title) }
    }

    suspend fun postCategoryUpdate(category_id: String, title: String): Response<CategoryUpdate> {
        return withContext(Dispatchers.IO) {
            ends.postCategoryUpdate(
                "categoryUpdate",
                category_id = category_id,
                title = title,
                status = "1"
            )
        }
    }

    suspend fun postBatchList(product_id:String): Response<BatchList> {
        return withContext(Dispatchers.IO) { ends.postBatchList("batchList",product_id) }
    }

    suspend fun postBatchAdd(
        product_id: String,
        title: String,
        qty: String
    ): Response<BatchAdd> {
        return withContext(Dispatchers.IO) {
            ends.postBatchAdd(
                "batchAdd",
                product_id = product_id,
                title = title,
                qty =qty
            )
        }
    }

    suspend fun postBatchUpdate(batch_id: String, title: String): Response<BatchUpdate> {
        return withContext(Dispatchers.IO) {
            ends.postBatchUpdate(
                "batchUpdate",
                batch_id = batch_id,
                title = title,
                status = "1"
            )
        }
    }



    suspend fun postAddQty(batch_id: String, qty: String): Response<AddQty> {
        return withContext(Dispatchers.IO) {
            ends.postAddQty(
                "addQty",
                batch_id = batch_id,
                qty = qty,
            )
        }
    }

    suspend fun postMinusQty(batch_id: String, qty: String): Response<MinusQty> {
        return withContext(Dispatchers.IO) {
            ends.postMinusQty(
                "minusQty",
                batch_id = batch_id,
                qty = qty,
            )
        }
    }

}