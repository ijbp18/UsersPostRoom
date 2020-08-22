package co.com.ceiba.mobile.pruebadeingreso.data.repository

import androidx.lifecycle.LiveData
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse

interface RepositoryDB {
    fun getAllUsers(): LiveData<List<UserResponse>>
    suspend fun addUsers(users: List<UserResponse>)
}
