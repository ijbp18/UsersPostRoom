package com.home.userspostroom.utiltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.data.repository.RepositoryDB

class FakeEmptyUserRepositoryDB:RepositoryDB {

    private val mockEmpty = MutableLiveData<List<UserResponse>>()

    override fun getAllUsers(): LiveData<List<UserResponse>> {
        return mockEmpty
    }

    override suspend fun addUsers(users: List<UserResponse>) {
        TODO("Not yet implemented")
    }

}