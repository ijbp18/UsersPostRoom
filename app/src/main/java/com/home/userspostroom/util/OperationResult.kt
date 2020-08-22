package com.home.userspostroom.util

sealed class OperationResult<out T> {
    data class Success<T>(val data: List<T>?) : OperationResult<T>()
    data class Failure(val exception:Exception?) : OperationResult<Nothing>()
}