package com.serasa.crud_room.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serasa.crud_room.model.Appointment
import com.serasa.crud_room.model.AppointmentWithRelations
import com.serasa.crud_room.model.DoctorWithCategory
import com.serasa.crud_room.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val repository: AppointmentRepository
): ViewModel() {

    private val _appoinment = MutableLiveData<List<AppointmentWithRelations>>()
    var appointment: LiveData<List<AppointmentWithRelations>> = _appoinment

    private val _gender = MutableLiveData<List<AppointmentWithRelations>>()
    var gender: LiveData<List<AppointmentWithRelations>> = _gender

    private val _category = MutableLiveData<List<AppointmentWithRelations>>()
    var category: LiveData<List<AppointmentWithRelations>> = _category

    private val _categoryById = MutableLiveData<DoctorWithCategory>()
    var categoryById: LiveData<DoctorWithCategory> = _categoryById

    fun fetchAppointment() {
        _appoinment.value = repository.fetchAppointment()
    }

    fun fetchFilteredAppointmentOfGender(gender: String) {
        _appoinment.value = repository.fetchFilteredAppoinmentOfGender(gender)
    }

    fun fetchFilteredAppointmentOfCategory(category: String) {
        _category.value = repository.fetchFilteredAppoinmentOfCategory(category)
    }

//    fun fetchCategoryIdByDoctor(id: Long) {
//        _categoryById.value = repository.fetchCategoryIdByDoctor(id)
//    }

    fun insertAppointment(appointment: Appointment) {
        repository.insertAppointment(appointment)
    }

    fun updateAppointment(appointmentWithRelations: AppointmentWithRelations) {
        repository.updateAppointment(appointmentWithRelations)
    }

    fun deleteAppointment(appointment: Appointment) {
        repository.deleteAppoitment(appointment)
    }
}