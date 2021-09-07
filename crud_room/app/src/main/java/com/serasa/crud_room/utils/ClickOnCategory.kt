package com.serasa.crud_room.utils

import com.serasa.crud_room.model.Category

interface ClickOnCategory {

    fun onClickUpdate(category: Category)

    fun onClickSave(category: Category)

    fun onClcikDelete(category: Category)
}