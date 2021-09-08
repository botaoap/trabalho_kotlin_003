package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.Patient

@Dao
interface PatientDAO {

    @Query("SELECT * FROM Patient")
    fun getPatients(): List<Patient>

    @Insert
    fun insertPatient(insert: Patient)

    @Delete
    fun deletePatient(delete: Patient)

    @Update
    fun updatePatientTest(patient: Patient)

}