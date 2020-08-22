package com.home.userspostroom.ui.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import com.home.userspostroom.R
import com.home.userspostroom.util.OnItemSelected
import java.util.*

class UsersAdapter(private val onItemSelectedListener: OnItemSelected<UserResponse>) : RecyclerView.Adapter<UsersViewHolder>(){

    private var userItems: List<UserResponse> = arrayListOf()
    private var userItemsFiltered: List<UserResponse> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
            UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false), onItemSelectedListener)

    override fun getItemCount(): Int = userItemsFiltered.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) = holder.bind(userItemsFiltered[position])

    fun setData(userItems: List<UserResponse>) {
        this.userItems = userItems
        this.userItemsFiltered = userItems
        notifyDataSetChanged()
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                userItemsFiltered = if (charString.isEmpty()) {
                    userItems
                } else {
                    val filteredList: MutableList<UserResponse> = ArrayList<UserResponse>()
                    for (row in userItems) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = userItemsFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                userItemsFiltered = filterResults.values as ArrayList<UserResponse>
                notifyDataSetChanged()
            }
        }
    }
}