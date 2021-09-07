package com.serasa.crud_room.utils

import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.model.DoctorWithCategory

interface ClickOnDoctor {

    fun onClickUpdate(doctorWithCategory: DoctorWithCategory)

    fun onClickSave(doctorWithCategory: DoctorWithCategory)

    fun onClcikDelete(doctorWithCategory: DoctorWithCategory)
}