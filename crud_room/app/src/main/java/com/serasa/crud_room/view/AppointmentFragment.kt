package com.serasa.crud_room.view

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.serasa.crud_room.R
import com.serasa.crud_room.adapter.AppointmentAdapter
import com.serasa.crud_room.databinding.AppointmentFragmentBinding
import com.serasa.crud_room.databinding.ItemAppointmentBinding
import com.serasa.crud_room.model.*
import com.serasa.crud_room.utils.ClickOnAppointment
import com.serasa.crud_room.utils.hideKeyboard
import com.serasa.crud_room.view_activity.AppointmentActivity
import com.serasa.crud_room.view_model.AppointmentViewModel
import com.serasa.crud_room.view_model.CategoryViewModel
import com.serasa.crud_room.view_model.DoctorViewModel
import com.serasa.crud_room.view_model.PatientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment(R.layout.appointment_fragment), ClickOnAppointment {

    companion object {
        fun newInstance() = AppointmentFragment()
    }

    private lateinit var viewModel: AppointmentViewModel
    private lateinit var viewModelPatient: PatientViewModel
    private lateinit var viewModelDoctor: DoctorViewModel
    private lateinit var viewModelCategory: CategoryViewModel
    private lateinit var binding: AppointmentFragmentBinding
    private lateinit var adapter: AppointmentAdapter
    private lateinit var recycler: RecyclerView
    private var PATIENT: List<Patient> = listOf()
    private var DOCTOR: List<DoctorWithCategory> = listOf()
    private var GENDER: List<String> = listOf()
    private var genderObject : String? = null
    private var CATEGORY: List<Category> = listOf()
    private var categoryObject : Category? = null

    private val observerAppointment = Observer<List<AppointmentWithRelations>> {
        if (it.size == 0) {
            Snackbar.make(requireView(), "Nobody is here", Snackbar.LENGTH_LONG).show()
        }
        adapter.refresh(it, PATIENT, DOCTOR)
    }

    private val observerFilteredGender = Observer<List<AppointmentWithRelations>> {
        if (it.size == 0) {
            Snackbar.make(requireView(), "Nobody is here", Snackbar.LENGTH_LONG).show()
        }
        adapter.refresh(it, PATIENT, DOCTOR)
    }

    private val observerFilteredCategory = Observer<List<AppointmentWithRelations>> {
        if (it.size == 0) {
            Snackbar.make(requireView(), "Nobody is here", Snackbar.LENGTH_LONG).show()
        }
        adapter.refresh(it, PATIENT, DOCTOR)
    }

    private val observerDoctorById = Observer<DoctorWithCategory> {

    }

    private val observerCategoryById = Observer<DoctorWithCategory> {

    }

    private val observerGender = Observer<List<String>> {
        GENDER = it
    }

    private val observerCategory = Observer<List<Category>> {
        CATEGORY = it
    }

    private val observerPatient = Observer<List<Patient>> {
        PATIENT = it
        viewModel.fetchAppointment()
    }

    private val observerDoctor = Observer<List<DoctorWithCategory>> {
        DOCTOR = it
        viewModelPatient.fetchPatient()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        executeComponents()
        searchFiltered()
    }

    fun loadComponents(view: View) {
        binding = AppointmentFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        viewModelPatient = ViewModelProvider(this).get(PatientViewModel::class.java)
        viewModelDoctor = ViewModelProvider(this).get(DoctorViewModel::class.java)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        adapter = AppointmentAdapter(this)
        recycler = binding.recyclerViewAppointment
    }

    fun executeComponents() {

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.appointment.observe(viewLifecycleOwner, observerAppointment)
        viewModel.gender.observe(viewLifecycleOwner, observerFilteredGender)
        viewModel.category.observe(viewLifecycleOwner, observerFilteredCategory)
        viewModel.categoryById
        viewModelPatient.patient.observe(viewLifecycleOwner, observerPatient)
        viewModelDoctor.doctor.observe(viewLifecycleOwner, observerDoctor)
        viewModelDoctor.doctorByid.observe(viewLifecycleOwner, observerDoctorById)
        viewModelPatient.gender.observe(viewLifecycleOwner, observerGender)
        viewModelCategory.category.observe(viewLifecycleOwner, observerCategory)

        viewModelDoctor.fetchDoctors()



        binding.floatingActionButtonAddAppointment.setOnClickListener {
            Intent(requireContext(), AppointmentActivity::class.java).apply {
                startActivity(this)
            }
        }

    }

    fun searchFiltered() {
        binding.choiceChips.chipGender.setOnClickListener {
            binding.choiceChips.chipCategory.setChecked(false)
            if (binding.choiceChips.chipGender.isChecked) {
                binding.outlinedTextFieldsearchAppointment.hint = binding.choiceChips.chipGender.text

                viewModelPatient.fetchGender()

                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    GENDER
                ).let { arrayAdapter ->
                    (binding.autocompleteSearchAppointment as AutoCompleteTextView).let { autoComplete ->
                        autoComplete.setAdapter(arrayAdapter)
                    }
                }
                binding.autocompleteSearchAppointment.setOnItemClickListener { adapterView, view, i, l ->
                    genderObject = adapterView.getItemAtPosition(i) as String
                }


            } else {
                binding.outlinedTextFieldsearchAppointment.hint = "Search"
            }

        }

        binding.choiceChips.chipCategory.setOnClickListener {
            binding.choiceChips.chipGender.setChecked(false)
            if (binding.choiceChips.chipCategory.isChecked) {
                binding.outlinedTextFieldsearchAppointment.hint = binding.choiceChips.chipCategory.text

                viewModelCategory.fetchCategories()

                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    CATEGORY
                ).let { arrayAdapter ->
                    (binding.autocompleteSearchAppointment as AutoCompleteTextView).let { autoComplete ->
                        autoComplete.setAdapter(arrayAdapter)
                    }
                }

                binding.autocompleteSearchAppointment.setOnItemClickListener { adapterView, view, i, l ->
                    categoryObject = adapterView.getItemAtPosition(i) as Category
                }

            } else {

                binding.outlinedTextFieldsearchAppointment.hint = "Search"
            }
        }

        binding.floatingActionButtonSearchAppointment.setOnClickListener {
            requireActivity().hideKeyboard()
            if (!genderObject.isNullOrEmpty()) {
                viewModel.fetchFilteredAppointmentOfGender(genderObject!!)
            }
            if (!categoryObject?.name.isNullOrEmpty()) {
                viewModel.fetchFilteredAppointmentOfCategory(categoryObject?.name!!)
            }
            if (binding.autocompleteSearchAppointment.text.isNullOrEmpty()){
                viewModel.fetchAppointment()
            }
            genderObject = ""
            categoryObject = Category(name = "")
            binding.autocompleteSearchAppointment.setText("")
            binding.autocompleteSearchAppointment.clearFocus()
        }

    }

    override fun onClickUpdate(appointmentWithRelations: AppointmentWithRelations) {

    }

    override fun onClickSave(appointmentWithRelations: AppointmentWithRelations) {
        viewModel.updateAppointment(appointmentWithRelations)
        viewModel.fetchAppointment()
    }

    override fun onClcikDelete(appointmentWithRelations: AppointmentWithRelations) {
        AlertDialog.Builder(context)
            .setTitle("Do you want to delete this appointment with Doctor " +
                    "${appointmentWithRelations.doctor!!.name}?")
            .setMessage("This will delete from Database. Are you sure?")
            .setPositiveButton(R.string.ok){dialog, which ->
                viewModel.deleteAppointment(appointmentWithRelations.appointment!!)
                viewModel.fetchAppointment()
            }
            .setNegativeButton(R.string.cancel){dialog,which ->

            }
            .create()
            .show()
    }

}