package com.home.userspostroom.ui.userdetail

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import com.home.userspostroom.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_post.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModel()
    private lateinit var userSelected: UserResponse
    private val adapter: PostsAdapter = PostsAdapter()
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        getIntentData()
        renderUserSelected()
        subscribeUi()
        initRecyclerView()
    }

    private fun getIntentData() {
        val intent: Intent = intent
        userSelected = intent.getSerializableExtra("itemSel") as UserResponse
        viewModel.retrievePostData(userSelected.id)
    }

    private fun initRecyclerView() {
        adapter.setData(emptyList())
        recyclerViewPostsResults.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewPostsResults.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.postList.observe(this, renderPostList)
        viewModel.progressVisibility.observe(this, isViewLoadingObserver)
        viewModel.responseErrorMessage.observe(this, showMessageError)
    }

    private val isViewLoadingObserver = Observer<Boolean> { progressBar(it) }

    private val renderPostList = Observer<List<PostResponse>> { adapter.setData(it) }

    private val showMessageError = Observer<String> { showToast(it) }

    private fun Context.progressBar(isVisibility: Boolean) {

        if (isVisibility) {
            dialog = SpotsDialog.Builder().setContext(this)
                    .setMessage(getString(R.string.message_dialog))
                    .setCancelable(false)
                    .build()
                    .apply {
                        show()
                    }
        } else dialog.dismiss()

    }

    private fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun renderUserSelected() {
        userSelected.let {
            name.text = userSelected.name
            phone.text = userSelected.phone
            email.text = userSelected.email
        }
    }

}