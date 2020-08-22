package com.home.userspostroom.utiltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.data.repository.RepositoryDB

class FakeUserRepositoryDB:RepositoryDB {

    //Experimental
    private val mockList:MutableList<UserResponse> = mutableListOf()
    private val mockEmpty = MutableLiveData<List<UserResponse>>()

    init {
        mockData()
    }

    private fun mockData(){

        mockList.add(UserResponse(1, "Jack Black", "Jack_black@gmail.com", "1-230-555-8001 x53332"))
        mockList.add(UserResponse(2, "Jane Doe", "Jane_doe@gmail.com", "1-987-223-3242 x51142"))
        mockEmpty.postValue(mockList)

    }

    override fun getAllUsers(): LiveData<List<UserResponse>> {
        return mockEmpty
    }

    override suspend fun addUsers(users: List<UserResponse>) {
        TODO("Not yet implemented")
    }
}