package com.home.userspostroom.utiltest

import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.data.repository.Repository
import com.home.userspostroom.util.OperationResult

class FakeUserRepository : Repository {

    private val mockList: MutableList<UserResponse> = mutableListOf()
    private val mockPostList: MutableList<PostResponse> = mutableListOf()

    init {
        mockData()
    }

    private fun mockData() {

        mockList.add(UserResponse(0, "Jhon Doe", "Jhon_doe@gmail.com", "1-770-736-8031 x56442"))
        mockList.add(UserResponse(1, "Jack Black", "Jack_black@gmail.com", "1-230-555-8001 x53332"))
        mockList.add(UserResponse(2, "Jane Doe", "Jane_doe@gmail.com", "1-987-223-3242 x51142"))
        mockList.add(UserResponse(3, "Jack White", "Jack_white@gmail.com", "1-550-441-1122 x56556"))

        mockPostList.add(PostResponse(3, 1, "qui est esse", "est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla"))
    }

    override suspend fun getUsers(): OperationResult<UserResponse> {
        return OperationResult.Success(mockList)
    }

    override suspend fun getPostsUser(userid: Int): OperationResult<PostResponse> {
        return OperationResult.Success(mockPostList)
    }
}