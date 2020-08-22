package com.home.userspostroom.ui.userslist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.ui.userlist.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import com.home.userspostroom.R
import com.home.userspostroom.ui.userdetail.PostActivity
import com.home.userspostroom.util.OnItemSelected
import com.home.userspostroom.util.SearchViewQueryTextCallback
import com.home.userspostroom.util.addTextChangedListener
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: UsersViewModel by viewModel()
    private lateinit var dialog: AlertDialog

    private val onItemSelectedListener: OnItemSelected<UserResponse> =
            object : OnItemSelected<UserResponse> {
                override fun onItemSelected(item: UserResponse) {
                    val intent = Intent(applicationContext, PostActivity::class.java)
                    intent.putExtra("itemSel", item)
                    startActivity(intent)
                }
            }

    private val adapter: UsersAdapter = UsersAdapter(onItemSelectedListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeUi()
        initRecyclerView()
        initSearchComponentHelper()
    }

    private fun initSearchComponentHelper() {

        val listener = object : SearchViewQueryTextCallback {
            override fun onTextChanged(query: CharSequence) {
                adapter.getFilter()?.filter(query)
            }
        }
        editTextSearch.addTextChangedListener(listener)
    }

    private fun initRecyclerView() {
        adapter.setData(emptyList())
        recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        recyclerViewSearchResults.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.usersList.observe(this, renderUserList)
        viewModel.progressVisibility.observe(this, isViewLoadingObserver)
        viewModel.isEmptyList.observe(this, isEmptyUserlist)
    }

    private val isViewLoadingObserver = Observer<Boolean> { progressBar(it) }

    private val renderUserList = Observer<List<UserResponse>> { adapter.setData(it) }

    private val isEmptyUserlist = Observer<String> { showSnakbar(it) }

    private fun showSnakbar(message: String) {
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show()
    }

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

    override fun onResume() {
        viewModel.retrieveUsersDefault()
        super.onResume()
    }
}