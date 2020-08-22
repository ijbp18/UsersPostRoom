package com.home.userspostroom.ui.userslist

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import com.home.userspostroom.R
import com.home.userspostroom.util.OnItemSelected

class UsersViewHolder(private val view: View, private val onItemSelectedListener: OnItemSelected<UserResponse>) : RecyclerView.ViewHolder(view) {

    fun bind(item: UserResponse) {

        val name = view.findViewById<TextView>(R.id.name)
        name.text = item.name

        val email = view.findViewById<TextView>(R.id.email)
        email.text = item.email

        val phone = view.findViewById<TextView>(R.id.phone)
        phone.text = item.phone

        val btnShowPost = view.findViewById<Button>(R.id.btn_view_post)

        btnShowPost.setOnClickListener {
            onItemSelectedListener.onItemSelected(item)
        }
    }
}