package com.serasa.crud_room.view_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.databinding.ActivityAppointmentBinding
import com.serasa.crud_room.model.Appointment
import com.serasa.crud_room.model.DoctorWithCategory
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.view.AppointmentFragment
import com.serasa.crud_room.view_model.AppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    private lateinit var viewModel: AppointmentViewModel
    private lateinit var repository: AppDatabase
    private val PATIENT: MutableList<Patient> = mutableListOf()
    private var patientObject : Patient? = null
    private val DOCTOR: MutableList<DoctorWithCategory> = mutableListOf()
    private var doctorObject : DoctorWithCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadComponents()
        loadPatient()
        loadDoctor()
        executeComponents()
    }

    fun loadComponents() {
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        repository = AppDatabase.getDatabase(this)
    }

    fun loadPatient() {
        PATIENT.addAll(repository.patientDAO().getPatients())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            PATIENT
        )

        val textView = binding.autoCompletePatienteAppointment as AutoCompleteTextView
        textView.setAdapter(adapter)

        binding.autoCompletePatienteAppointment.setOnItemClickListener { adapterView, view, i, l ->
            patientObject = adapterView.getItemAtPosition(i) as Patient
        }
    }

    fun loadDoctor() {
        DOCTOR.addAll(repository.doctorDAO().getDoctor())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            DOCTOR
        )

        val textView = binding.autoCompleteDoctorAppointment as AutoCompleteTextView
        textView.setAdapter(adapter)

        binding.autoCompleteDoctorAppointment.setOnItemClickListener { adapterView, view, i, l ->
            doctorObject = adapterView.getItemAtPosition(i) as DoctorWithCategory
        }
    }

    fun executeComponents() {
        binding.buttonCancel.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.buttonSave.setOnClickListener {
            viewModel.insertAppointment(
                appointment = Appointment(
                    docFk = doctorObject?.doctor?.id!!,
                    patFk = patientObject?.id!!
                )
            )
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}