package com.serasa.crud_room.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val repository: PatientRepository
): ViewModel() {

    private val _patient = MutableLiveData<List<Patient>>()
    var patient: LiveData<List<Patient>> = _patient

    private val _gender = MutableLiveData<List<String>>()
    var gender: LiveData<List<String>> = _gender

    private val _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    fun fetchPatient() {
        _patient.value = repository.fetchPatients()
    }

    fun fetchGender() {
        _gender.value = repository.fetchGender()
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