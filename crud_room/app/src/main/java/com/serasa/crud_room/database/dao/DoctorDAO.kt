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

    @Insert
    fun insertCategory(category: Category): Long

    fun insert(doctorWithCategory: DoctorWithCategory) {
        insertCategory(doctorWithCategory.category!!)
        doctorWithCategory.doctor?.let { doc ->
            insertDoctor(doc)
        }
    }

    @Delete
    fun delete(doct: Doctor)

    @Delete
    fun deleteDoctor(doctorWithCategory: DoctorWithCategory) {
        delete(doctorWithCategory.doctor!!)
    }

    @Query("UPDATE Doctor SET doc_name = :nameUpdate, categoryFk = :idCategory WHERE doc_id = :idDoctor")
    fun updateDoctor(nameUpdate: String, idCategory: Long, idDoctor: Long)

}