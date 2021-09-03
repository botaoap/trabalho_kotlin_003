package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.*

@Dao
interface AppointmentDAO {

    @Transaction
    @Query("SELECT * FROM Appointment")
    fun getAppointments(): List<AppointmentWithRelations>

    @Insert
    fun insertAppointment(appointment: Appointment)

    @Insert
    fun insertDoctor(doctor: Doctor): Long

    @Insert
    fun insertPatient(patient: Patient): Long

    fun insert(appointmentWithRelations: AppointmentWithRelations) {
        appointmentWithRelations.patient?.let { pat ->
            insertPatient(pat)
        }
        appointmentWithRelations.doctor?.let { doc ->
            insertDoctor(doc)
        }
        appointmentWithRelations.appointment?.let { appointment ->
            insertAppointment(appointment)
        }
    }

    @Delete
    fun delete(appointment: Appointment)

    fun deleteAppointment(appointmentWithRelations: AppointmentWithRelations) {
        delete(appointmentWithRelations.appointment!!)
        appointmentWithRelations.appointment?.let { appoint ->
            delete(appoint)
        }
    }

    @Query("UPDATE Appointment SET patFk = :updatePat, docFk = :updateDoc WHERE appoint_id = :idAppoint")
    fun updateAppointment(updatePat: Long, updateDoc: Long, idAppoint: Long)
}