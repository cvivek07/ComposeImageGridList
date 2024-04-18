package com.vivekchoudhary.imageloadingapp.common

sealed class DataWrapper<T>(val data: T?) {
    class Success<T>(data: T) : DataWrapper<T>(data)
    class Loading<T>(data: T? = null) : DataWrapper<T>(data)
    class Failure<T>(data: T? = null, val cause: Throwable? = null) : DataWrapper<T>(data)

    fun onSuccess(lambda: (T) -> Unit): DataWrapper<T> {
        if (this is Success) {
            lambda(this.data!!)
        }
        return this
    }

    fun onFailure(lambda: (T?, Throwable?) -> Unit): DataWrapper<T> {
        if (this is Failure) {
            lambda(data, cause)
        }
        return this
    }


    fun onLoading(lambda: (T?) -> Unit): DataWrapper<T> {
        if (this is Loading) {
            lambda(data)
        }
        return this
    }
}