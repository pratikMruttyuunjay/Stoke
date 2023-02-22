package com.mruttyuunjay.stoke.di

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
import com.mruttyuunjay.stoke.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface Ends {

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postProductAdd(
        @Field("action") action: String,
        @Field("category_id") category_id: String,
        @Field("title") title: String,
    ): Response<ProductAdd>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postProductUpdate(
        @Field("action") action: String,
        @Field("product_id") product_id: String,
        @Field("status") status: String,
        @Field("title") title: String,
    ): Response<ProductUpdate>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postProductList(
        @Field("action") action: String,
        @Field("status") status: String,
    ): Response<ProductList>



    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postCategoryList(
        @Field("action") action: String,
        @Field("status") status: String,
    ): Response<CategoryList>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postCategoryAdd(
        @Field("action") action: String,
        @Field("title") title: String,
    ): Response<CategoryAdd>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postCategoryUpdate(
        @Field("action") action: String,
        @Field("category_id") category_id: String,
        @Field("status") status: String,
        @Field("title") title: String,
    ): Response<CategoryUpdate>



    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postBatchList(
        @Field("action") action: String,
    ): Response<BatchList>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postBatchAdd(
        @Field("action") action: String,
        @Field("product_id") product_id: String,
        @Field("title") title: String,
        @Field("qty") qty: String,
    ): Response<BatchAdd>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postBatchUpdate(
        @Field("action") action: String,
        @Field("batch_id") batch_id: String ,
        @Field("status") status: String ,
        @Field("title") title: String,
    ): Response<BatchUpdate>



    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postAddQty(
        @Field("action") action: String,
        @Field("batch_id") batch_id: String ,
        @Field("qty") qty: String,
    ): Response<AddQty>

    @FormUrlEncoded
    @POST(Constants.ROOT)
    suspend fun postMinusQty(
        @Field("action") action: String,
        @Field("batch_id") batch_id: String ,
        @Field("qty") qty: String,
    ): Response<MinusQty>

}