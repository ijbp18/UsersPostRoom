package co.com.ceiba.mobile.pruebadeingreso.data.repository

import androidx.lifecycle.LiveData
import com.home.userspostroom.data.database.dao.UserDao
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse

class RepositoryDBImpl(private val userDao: UserDao): RepositoryDB {
    override fun getAllUsers(): LiveData<List<UserResponse>> {
        return userDao.loadUsers()
    }

    override suspend fun addUsers(users: List<UserResponse>) {
        return userDao.saveUsers(users)
    }
}