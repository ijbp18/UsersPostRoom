package com.home.userspostroom.utiltest

import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.data.repository.Repository
import com.home.userspostroom.util.OperationResult

class FakeEmptyUserRepository:Repository {

    private val mockEmpty: List<UserResponse> = emptyList()
    override suspend fun getUsers(): OperationResult<UserResponse> {
        return OperationResult.Success(mockEmpty)
    }

    override suspend fun getPostsUser(userid: Int): OperationResult<PostResponse> {
        return OperationResult.Success(emptyList())
    }
}