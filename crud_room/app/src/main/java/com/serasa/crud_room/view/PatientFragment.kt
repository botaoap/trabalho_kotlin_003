package com.serasa.crud_room.view

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.serasa.crud_room.view_activity.PatientActivity
import com.serasa.crud_room.R
import com.serasa.crud_room.adapter.PatientAdapter
import com.serasa.crud_room.databinding.PatientFragmentBinding
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.utils.ClickOnPatient
import com.serasa.crud_room.view_model.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientFragment : Fragment(R.layout.patient_fragment), ClickOnPatient {

    companion object {
        fun newInstance() = PatientFragment()
    }

    private lateinit var viewModel: PatientViewModel
    private lateinit var binding: PatientFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientAdapter
    private var GENDER: List<String> = listOf()

    private val observerPatient = Observer<List<Patient>> {
        if (it.size == 0) {
            Snackbar.make(requireView(), "Nobody is here", Snackbar.LENGTH_LONG).show()
        }
        adapter.refresh(it, GENDER)
    }

    private val observerGender = Observer<List<String>> {
        GENDER = it
        viewModel.fetchPatient()
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), "Error Error", Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding = PatientFragmentBinding.bind(view)
//        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        executeComponents()
    }

    fun loadComponents(view: View) {
        binding = PatientFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
        adapter = PatientAdapter(this)
        recyclerView = binding.recyclerViewPatient

        viewModel.patient.observe(viewLifecycleOwner, observerPatient)
        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.gender.observe(viewLifecycleOwner, observerGender)


    }

    fun executeComponents() {

        viewModel.fetchGender()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        binding.floatingActionButtonAddPatient.setOnClickListener {
            Intent(requireContext(), PatientActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onClickUpdate(patient: Patient) {

    }

    override fun onClickSave(patient: Patient) {
        viewModel.updatePatientTeste(patient)
        viewModel.fetchPatient()
    }

    override fun onClcikDelete(patient: Patient) {
        AlertDialog.Builder(context)
            .setTitle("Do you want to delete ${patient.name}?")
            .setMessage("This will delete from Database. Are you sure?")
                .setPositiveButton(R.string.ok){dialog, which ->
                    viewModel.deletePatient(patient)
                    viewModel.fetchPatient()
                }
                .setNegativeButton(R.string.cancel){dialog,which ->

                }
                .create()
                .show()
    }
}