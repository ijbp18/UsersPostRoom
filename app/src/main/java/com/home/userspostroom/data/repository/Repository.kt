package co.com.ceiba.mobile.pruebadeingreso.data.repository

import com.home.userspostroom.util.OperationResult
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse

interface Repository {
    suspend fun getUsers(): OperationResult<UserResponse>
    suspend fun getPostsUser(userid: Int): OperationResult<PostResponse>
}