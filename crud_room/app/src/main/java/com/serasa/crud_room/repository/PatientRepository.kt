package com.serasa.crud_room.repository

import com.serasa.crud_room.database.dao.PatientDAO
import com.serasa.crud_room.model.Patient
import javax.inject.Inject


class PatientRepository @Inject constructor(
    private val repository: PatientDAO
){
    fun fetchPatients(): List<Patient> {
        return repository.getPatients()
    }

    fun updatePatient(name: String, gender: String, age: Int, idPatient: Long ) {
        return repository.updatePatient(name, gender, age, idPatient)
    }

    fun updatePatientTeste(patient: Patient) {
        return repository.updatePatientTest(patient)
    }


    fun deletePatient(patient: Patient) {
        repository.deletePatient(patient)
    }

    fun insertPatient(patient: Patient) {
        repository.insertPatient(patient)
    }
}