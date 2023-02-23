package com.mruttyuunjay.stoke.utils

import android.util.Log
import retrofit2.Response


interface ResponseModel {
    val status: String
    val message: String
}

/*
where T : ResponseModel : this is a type constraint,
it means that the type parameter T must implement the ResponseModel interface,
this means that any implementation of T passed to this function must implement the ResponseModel interface.
*/
fun <T> response (response: Response<T>): Resource<T> where T : ResponseModel {

    return when {
        response.body()?.status?.contains("0") == true -> {
            println()
            Log.wtf("message",response.body()?.message)
            Log.wtf("status",response.body()?.status)
            println()
            Resource.Error(message = response.body()?.message)
        }
        response.isSuccessful -> {
            Resource.Success(response.body()!!)
        }
        else -> {
            Resource.Error(message = response.message())
        }
    }
}