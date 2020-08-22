package com.home.userspostroom.ui.userdetail

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.PostResponse
import com.home.userspostroom.R

class PostsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: PostResponse) {

        val title = view.findViewById<TextView>(R.id.title)
        title.text = item.title

        val body = view.findViewById<TextView>(R.id.body)
        body.text = item.body

    }
}