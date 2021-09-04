package com.serasa.crud_room.view_model

import android.net.LinkAddress
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val repository: PatientRepository
): ViewModel() {

    private val _patient = MutableLiveData<List<Patient>>()
    var patient: LiveData<List<Patient>> = _patient

    private val _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    fun fetchPatient() {
        _patient.value = repository.fetchPatients()
    }

    fun updatePatient(name: String, gender: String, age: Int, idPatient: Long) {
        repository.updatePatient(name, gender, age, idPatient)
    }

    fun updatePatientTeste(patient: Patient) {
        repository.updatePatientTeste(patient)
    }

    fun deletePatient(patient: Patient) {
        repository.deletePatient(patient)
    }

    fun insertPatient(patient: Patient) {
        repository.insertPatient(patient)
    }
}