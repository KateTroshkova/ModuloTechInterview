package com.noveogroup.modulotechinterview.common.android.delegate

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardDelegate(
    private val activity: Activity
) {

    private val input: InputMethodManager
        get() = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun showKeyboard(target: View): Unit = with(input) {
        showSoftInput(
            target.apply { requestFocus() },
            InputMethodManager.SHOW_FORCED
        )
    }

    fun hideKeyboard() {
        activity.currentFocus?.apply { input.hideSoftInputFromWindow(windowToken, 0) }
    }
}
