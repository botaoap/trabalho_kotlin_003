package com.serasa.crud_room.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.serasa.crud_room.R


fun FragmentActivity.replaceView(fragment: Fragment, @IdRes idContainer: Int = R.id.container) {
    supportFragmentManager.beginTransaction()
        .replace(idContainer, fragment)
        .commitNow()
}