package com.serasa.crud_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.serasa.crud_room.databinding.ActivityPatientBinding
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.view_model.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    private lateinit var viewModel: PatientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadComponents()
        executeComponents()
    }

    fun loadComponents() {
        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
    }

    fun executeComponents() {
        binding.buttonCancel.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.buttonSave.setOnClickListener {
            viewModel.insertPatient(
                    patient = Patient(
                            name = binding.editTextNameAdd.text.toString(),
                            gender = binding.editTextGenderAdd.text.toString(),
                            age = binding.editTextAge.text.toString().toInt()
                    )
            )
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }

        }
    }
}