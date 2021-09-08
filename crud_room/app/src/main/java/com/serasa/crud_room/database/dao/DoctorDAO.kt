package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.model.DoctorWithCategory

@Dao
interface DoctorDAO{

    @Transaction
    @Query("SELECT * FROM Doctor")
    fun getDoctor(): List<DoctorWithCategory>

    @Insert
    fun insertDoctor(doctor: Doctor)

    @Delete
    fun deleteDoctor(doctor: Doctor)

    @Update
    fun updateDoctorTest(doctor: Doctor)

}