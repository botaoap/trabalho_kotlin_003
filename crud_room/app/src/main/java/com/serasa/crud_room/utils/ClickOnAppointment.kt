package com.serasa.crud_room.utils

import com.serasa.crud_room.model.Appointment
import com.serasa.crud_room.model.AppointmentWithRelations
import com.serasa.crud_room.model.Category

interface ClickOnAppointment {

    fun onClickUpdate(appointmentWithRelations: AppointmentWithRelations)

    fun onClickSave(appointmentWithRelations: AppointmentWithRelations)

    fun onClcikDelete(appointmentWithRelations: AppointmentWithRelations)
}