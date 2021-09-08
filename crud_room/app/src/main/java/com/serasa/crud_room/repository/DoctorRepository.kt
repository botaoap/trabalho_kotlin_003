package com.serasa.crud_room.repository

import com.serasa.crud_room.database.dao.DoctorDAO
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.model.DoctorWithCategory
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    private val dao : DoctorDAO
){
    fun fetchDoctors() : List<DoctorWithCategory>{
        return dao.getDoctor()
    }

    fun insertDoctor(doctor: Doctor) {
        dao.insertDoctor(doctor)
    }

    fun updateDoctor(doctorWithCategory: DoctorWithCategory) {
        dao.updateDoctorTest(doctorWithCategory.doctor!!)
    }

    fun deleteDoctor(doctor: Doctor) {
        dao.deleteDoctor(doctor)
    }
}