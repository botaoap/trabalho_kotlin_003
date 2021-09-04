package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.Patient

@Dao
interface PatientDAO {

    @Query("SELECT * FROM Patient")
    fun getPatients(): List<Patient>

    @Insert
    fun insert(list: List<Patient>)

    @Insert
    fun insertPatient(insert: Patient)

    @Delete
    fun deletePatient(delete: Patient)

    @Query("UPDATE Patient SET pat_name = :nameUpdate, pat_gender = :genderUpdate, pat_age = :ageUpdate WHERE pat_id = :idPatient")
    fun updatePatient(nameUpdate: String, genderUpdate: String, ageUpdate: Int, idPatient: Long)

    @Update
    fun updatePatientTest(patient: Patient)

}