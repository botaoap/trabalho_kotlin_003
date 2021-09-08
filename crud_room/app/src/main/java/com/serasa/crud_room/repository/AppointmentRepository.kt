package com.serasa.crud_room.repository

import com.serasa.crud_room.database.dao.AppointmentDAO
import com.serasa.crud_room.model.Appointment
import com.serasa.crud_room.model.AppointmentWithRelations
import javax.inject.Inject


class AppointmentRepository @Inject constructor(
    private val dao: AppointmentDAO
) {

    fun fetchAppointment(): List<AppointmentWithRelations> {
        return dao.getAppointments()
    }

    fun fetchFilteredAppoinmentOfGender(gender: String): List<AppointmentWithRelations> {
        return dao.getFilteredAppointmentOfGender(gender)
    }

    fun fetchFilteredAppoinmentOfCategory(category: String): List<AppointmentWithRelations> {
        return dao.getFilteredAppointmentOfCategory(category)
    }


    fun insertAppointment(appointment: Appointment) {
        dao.insertAppointment(appointment)
    }

    fun updateAppointment(appointmentWithRelations: AppointmentWithRelations) {
        dao.updateAppointmentTest(appointmentWithRelations.appointment!!)
    }

    fun deleteAppoitment(appointment: Appointment) {
        return dao.deleteAppointment(appointment)
    }
}