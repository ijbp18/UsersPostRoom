package co.com.ceiba.mobile.pruebadeingreso.data.repository

import com.home.userspostroom.util.OperationResult
import co.com.ceiba.mobile.pruebadeingreso.data.network.APIService
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse


class RepositoryImpl(private val myServiceAPI: APIService) : Repository {

    override suspend fun getUsers(): OperationResult<UserResponse> {

        try {
            val response = myServiceAPI.getUsers()
            response.let {
                return OperationResult.Success(it)
            }
        } catch (e: Exception) {
            return OperationResult.Failure(e)
        }
    }

    override suspend fun getPostsUser(userid: Int): OperationResult<PostResponse> {

        try {
            val response = myServiceAPI.getPostsUser(userid)
            response.let {
                return OperationResult.Success(it)
            }
        } catch (e: Exception) {
            return OperationResult.Failure(e)
        }
    }
}