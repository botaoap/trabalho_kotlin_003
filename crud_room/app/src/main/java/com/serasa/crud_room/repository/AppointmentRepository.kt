package com.serasa.crud_room.repository

import com.serasa.crud_room.database.dao.AppointmentDAO
import com.serasa.crud_room.database.dao.CategoryDAO
import com.serasa.crud_room.model.Appointment
import com.serasa.crud_room.model.AppointmentWithRelations
import com.serasa.crud_room.model.DoctorWithCategory
import javax.inject.Inject


class AppointmentRepository @Inject constructor(
    private val dao: AppointmentDAO,
    private val daoCategory: CategoryDAO
) {

    fun fetchAppointment(): List<AppointmentWithRelations> {
        val appointments = dao.getAppointments()
        appointments.forEach {
            it.doctor?.categoryFk?.let { id ->
                it.doctor.category = daoCategory.getCategoryById(id)
            }
        }
        return appointments
    }

    fun fetchFilteredAppoinmentOfGender(gender: String): List<AppointmentWithRelations> {
        val appointments = dao.getFilteredAppointmentOfGender(gender)
        appointments.forEach {
            it.doctor?.categoryFk?.let { id ->
                it.doctor.category = daoCategory.getCategoryById(id)
            }
        }
        return appointments
    }

    fun fetchFilteredAppoinmentOfCategory(category: String): List<AppointmentWithRelations> {
        val appointments = dao.getFilteredAppointmentOfCategory(category)
        appointments.forEach {
            it.doctor?.categoryFk?.let { id ->
                it.doctor.category = daoCategory.getCategoryById(id)
            }
        }
        return appointments
    }

//    fun fetchCategoryIdByDoctor(id: Long): DoctorWithCategory {
//        return dao.getCategoryIdByDoctor(id)
//    }

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