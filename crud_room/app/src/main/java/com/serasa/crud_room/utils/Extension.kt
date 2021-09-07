package com.serasa.crud_room.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.serasa.crud_room.R


fun FragmentActivity.replaceView(fragment: Fragment, @IdRes idContainer: Int = R.id.container) {
    supportFragmentManager.beginTransaction()
        .replace(idContainer, fragment)
        .commitNow()
}

fun FragmentActivity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}