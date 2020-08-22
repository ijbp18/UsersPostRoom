package co.com.ceiba.mobile.pruebadeingreso.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.home.userspostroom.data.database.dao.UserDao
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse


@Database(entities = [UserResponse::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
