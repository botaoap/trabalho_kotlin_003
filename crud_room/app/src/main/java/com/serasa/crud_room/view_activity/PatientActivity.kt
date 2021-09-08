package com.serasa.crud_room.view_activity

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import com.serasa.crud_room.databinding.ActivityPatientBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.view_model.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    private lateinit var viewModel: PatientViewModel
    private var GENDER: List<String> = listOf()
    private var genderObject : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadComponents()
        loadGender()
        executeComponents()
    }

    fun loadComponents() {
        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
    }

    fun loadGender() {
        GENDER = listOf("M","F","Other")

        val adapter = ArrayAdapter(
            this,
            R.layout.simple_dropdown_item_1line,
            GENDER
        )
        val textView = binding.autoCompleteGenderAdd as AutoCompleteTextView
        textView.setAdapter(adapter)

        binding.autoCompleteGenderAdd.setOnItemClickListener { adapterView, view, i, l ->
            genderObject = adapterView.getItemAtPosition(i) as String
        }

    }

    fun executeComponents() {
        binding.buttonCancel.setOnClickListener {
            finish()
        }
        binding.buttonSave.setOnClickListener {
            viewModel.insertPatient(
                    patient = Patient(
                            name = binding.editTextNameAdd.text.toString(),
                            gender = genderObject!!,
                            age = binding.editTextAge.text.toString().toInt()
                    )
            )
            finish()

        }
    }
}