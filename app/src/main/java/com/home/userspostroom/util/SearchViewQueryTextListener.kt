package com.home.userspostroom.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.home.userspostroom.util.SearchViewQueryTextCallback

fun EditText.addTextChangedListener(callback: SearchViewQueryTextCallback) {

    addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(query: Editable?) {
        }

        override fun beforeTextChanged(query: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(query: CharSequence, start: Int, before: Int, count: Int) {
            callback.onTextChanged(query)
        }
    })
}