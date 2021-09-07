package com.serasa.crud_room.utils

import com.serasa.crud_room.model.Patient

interface ClickOnPatient {

    fun onClickUpdate(patient: Patient)

    fun onClickSave(patient: Patient)

    fun onClcikDelete(patient: Patient)
}