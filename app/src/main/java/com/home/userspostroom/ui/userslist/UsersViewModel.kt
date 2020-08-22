package co.com.ceiba.mobile.pruebadeingreso.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.userspostroom.util.OperationResult
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.data.repository.Repository
import co.com.ceiba.mobile.pruebadeingreso.data.repository.RepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(private val remoteRepository: Repository, private val repositoryDB: RepositoryDB) : ViewModel() {

    private val _progressVisibility = MutableLiveData<Boolean>()
    val progressVisibility: LiveData<Boolean> get() = _progressVisibility

    private val _isEmptyList = MutableLiveData<String>()
    val isEmptyList: LiveData<String> = _isEmptyList

     val usersList = repositoryDB.getAllUsers()

    fun retrieveUsersDefault() {

        _progressVisibility.postValue(true)

        viewModelScope.launch {
            val result: OperationResult<UserResponse> = withContext(Dispatchers.IO) {
                remoteRepository.getUsers()
            }

            _progressVisibility.postValue(false)

            if (result is OperationResult.Success) {
               withContext(Dispatchers.IO) {
                   result.data?.let {
                       if (it.isNotEmpty()) repositoryDB.addUsers(it)
                   }
                }

                if (result.data.isNullOrEmpty()) {
                    _isEmptyList.value = "List is empty"
                }
            }
        }
    }

}