package com.home.userspostroom.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import co.com.ceiba.mobile.pruebadeingreso.data.repository.Repository
import com.home.userspostroom.util.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsViewModel(private val remoteRepository: Repository) : ViewModel() {

    private val _progressVisibility = MutableLiveData<Boolean>()
    val progressVisibility: LiveData<Boolean> get() = _progressVisibility

    private val _responseErrorMessage = MutableLiveData<String>()
    val responseErrorMessage: LiveData<String> get() = _responseErrorMessage

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _postList = MutableLiveData<List<PostResponse>>()
    val postList: LiveData<List<PostResponse>> = _postList

    fun retrievePostData(userId: Int) {
        _progressVisibility.value = true

        viewModelScope.launch {

            val result: OperationResult<PostResponse> = withContext(Dispatchers.IO) {
                remoteRepository.getPostsUser(userId)
            }

            if (result is OperationResult.Success) {
                if (result.data.isNullOrEmpty()) {
                    _isEmptyList.value = true
                } else _postList.value = result.data

            } else _responseErrorMessage.value = "Ocurrió un error al obtener las publicaciones del usuario."

            _progressVisibility.value = false
        }
    }
}