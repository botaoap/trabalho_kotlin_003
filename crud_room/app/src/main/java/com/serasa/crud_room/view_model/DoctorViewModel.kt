package com.serasa.crud_room.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.model.DoctorWithCategory
import com.serasa.crud_room.repository.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val repository: DoctorRepository
): ViewModel() {

    private val _doctor = MutableLiveData<List<DoctorWithCategory>>()
    var doctor: LiveData<List<DoctorWithCategory>> = _doctor


    fun fetchDoctors() {
        _doctor.value = repository.fetchDoctors()
    }

    fun insertDoctor(doctor: Doctor) {
        repository.insertDoctor(doctor)
    }

    fun updateDoctor(doctorWithCategory: DoctorWithCategory) {
        repository.updateDoctor(doctorWithCategory)
    }

    fun deleteDoctor(doctor: Doctor) {
        repository.deleteDoctor(doctor)
    }
}