package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.*

@Dao
interface AppointmentDAO {

    @Transaction
    @Query("SELECT * FROM Appointment")
    fun getAppointments(): List<AppointmentWithRelations>

    @Transaction
    @Query("SELECT ap.appoint_id, ap.docFk, ap.patFk, doc.doc_name, cat.cat_name, pat.pat_name, pat.pat_gender FROM Appointment as ap inner join Doctor as doc on doc.doc_id = ap.docFk inner join Category as cat on cat.cat_id = doc.categoryFk inner join Patient as pat on pat.pat_id = ap.patFk where lower(pat.pat_gender)  like lower(:gender)")
    fun getFilteredAppointmentOfGender(gender: String): List<AppointmentWithRelations>

    @Transaction
    @Query("SELECT ap.appoint_id, ap.docFk, ap.patFk, doc.doc_name, cat.cat_name, pat.pat_name, pat.pat_gender FROM Appointment as ap inner join Doctor as doc on doc.doc_id = ap.docFk inner join Category as cat on cat.cat_id = doc.categoryFk inner join Patient as pat on pat.pat_id = ap.patFk where lower(cat.cat_name)  like lower(:category)")
    fun getFilteredAppointmentOfCategory(category: String): List<AppointmentWithRelations>

    @Insert
    fun insertAppointment(appointment: Appointment)

    @Delete
    fun deleteAppointment(appointment: Appointment)

    @Update
    fun updateAppointmentTest(appointment: Appointment)
}