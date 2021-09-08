package com.serasa.crud_room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.serasa.crud_room.R
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.databinding.ItemAppointmentBinding
import com.serasa.crud_room.model.*
import com.serasa.crud_room.utils.ClickOnAppointment

class AppointmentAdapter(val onClick: ClickOnAppointment) :
    RecyclerView.Adapter<ItemAppointmentViewHolder>() {

    private val listOfAppointment = mutableListOf<AppointmentWithRelations>()
    private var PATIENT: List<Patient> = listOf()
    private var DOCTOR: List<DoctorWithCategory> = listOf()
    private lateinit var repository: AppDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAppointmentViewHolder {
        repository = AppDatabase.getDatabase(parent.context)

        LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
            .apply {
                return ItemAppointmentViewHolder(this)
            }
    }

    override fun onBindViewHolder(holder: ItemAppointmentViewHolder, position: Int) {
        listOfAppointment[position].apply {
            holder.bind(this, PATIENT, DOCTOR, onClick)
        }
    }

    override fun getItemCount(): Int {
        return listOfAppointment.size
    }

    fun refresh(
        newList: List<AppointmentWithRelations>,
        listOfPatient: List<Patient>,
        listOfDoctors: List<DoctorWithCategory>
    ) {
        listOfAppointment.clear()
        listOfAppointment.addAll(newList)
        PATIENT = listOfPatient
        DOCTOR = listOfDoctors
        notifyDataSetChanged()
    }
}

class ItemAppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemAppointmentBinding.bind(itemView)
    private var patientObject: Patient? = null
    private var doctorObject: DoctorWithCategory? = null

    fun bind(
        appointmentWithRelations: AppointmentWithRelations,
        patients: List<Patient>,
        doctors: List<DoctorWithCategory>,
        onClick: ClickOnAppointment
    ) {
        binding.autoCompleteDoctorAppointment.setText(appointmentWithRelations.doctor?.name)
        binding.autoCompletePatientAppointment.setText(appointmentWithRelations.patient?.name)
        binding.editTextIdAppointment.setText(appointmentWithRelations.appointment?.id.toString())
        binding.editTextDoctorCategoryAppointment.setText(appointmentWithRelations.doctor?.categoryFk.toString())
        binding.editTexPatientGenderAppointment.setText(appointmentWithRelations.patient?.gender)

        // TODO: Load AutoCompleteTextView with PATIENTS
        ArrayAdapter(
            itemView.context,
            android.R.layout.simple_dropdown_item_1line,
            patients
        ).let { arrayAdapterPatient ->
            (binding.autoCompletePatientAppointment as AutoCompleteTextView).let { autoComplete ->
                autoComplete.setAdapter(arrayAdapterPatient)
            }
        }
        // TODO: Load AutoCompleteTextView with DOCTORS
        ArrayAdapter(
            itemView.context,
            android.R.layout.simple_dropdown_item_1line,
            doctors
        ).let { arrayAdapterDoctor ->
            (binding.autoCompleteDoctorAppointment as AutoCompleteTextView).let { autoComplete ->
                autoComplete.setAdapter(arrayAdapterDoctor)
            }
        }

        binding.autoCompletePatientAppointment.setOnItemClickListener { adapterView, view, i, l ->
            patientObject = adapterView.getItemAtPosition(i) as Patient
        }

        binding.autoCompleteDoctorAppointment.setOnItemClickListener { adapterView, view, i, l ->
            doctorObject = adapterView.getItemAtPosition(i) as DoctorWithCategory
        }

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonDeleteAppointment)
            .setOnClickListener {
                onClick.onClcikDelete(appointmentWithRelations)
            }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonUpdateAppointment)
            .setOnClickListener {
                binding.autoCompleteDoctorAppointment.isEnabled = true
                binding.autoCompletePatientAppointment.isEnabled = true
                binding.floatingActionButtonUpdateAppointment.visibility = View.INVISIBLE
                binding.floatingActionButtonSaveAppointment.visibility = View.VISIBLE
                onClick.onClickUpdate(appointmentWithRelations)
            }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonSaveAppointment)
            .setOnClickListener {
                binding.autoCompleteDoctorAppointment.isEnabled = false
                binding.autoCompletePatientAppointment.isEnabled = false
                binding.floatingActionButtonUpdateAppointment.visibility = View.VISIBLE
                binding.floatingActionButtonSaveAppointment.visibility = View.INVISIBLE
                if (doctorObject != null) {
                    appointmentWithRelations.appointment?.docFk = doctorObject?.doctor?.id!!
                } else {
//                    appointmentWithRelations.doctor?.name =
//                        appointmentWithRelations.doctor?.name!!
                }
                if (patientObject != null) {
                    appointmentWithRelations.appointment?.patFk = patientObject?.id!!
                } else {
//                    appointmentWithRelations.patient?.name =
//                        appointmentWithRelations.patient?.name!!
                }
                onClick.onClickSave(appointmentWithRelations)
            }
//        binding.autoCompleteCategoryDoctor.setText(doctorWithCategory.category?.name, false)
    }
}