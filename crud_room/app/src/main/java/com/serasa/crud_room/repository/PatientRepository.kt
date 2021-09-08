package com.serasa.crud_room.repository

import com.serasa.crud_room.database.dao.PatientDAO
import com.serasa.crud_room.model.Patient
import javax.inject.Inject


class PatientRepository @Inject constructor(
    private val dao: PatientDAO
){
    fun fetchPatients(): List<Patient> {
        return dao.getPatients()
    }

    fun fetchGender(): List<String> {
        return listOf("M", "F", "Other")
    }

    fun updatePatientTeste(patient: Patient) {
        return dao.updatePatientTest(patient)
    }


    fun deletePatient(patient: Patient) {
        dao.deletePatient(patient)
    }

    fun insertPatient(patient: Patient) {
        dao.insertPatient(patient)
    }
}