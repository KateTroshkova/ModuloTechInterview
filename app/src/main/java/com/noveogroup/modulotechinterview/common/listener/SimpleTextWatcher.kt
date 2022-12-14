package com.noveogroup.modulotechinterview.common.listener

import android.text.Editable
import android.text.TextWatcher

open class SimpleTextWatcher : TextWatcher {

    override fun afterTextChanged(text: Editable?) {}
    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(text.toString())
    }

    open fun onTextChanged(text: String) {

    }
}
