package com.serasa.crud_room.view


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.serasa.crud_room.R
import com.serasa.crud_room.adapter.DoctorAdapter
import com.serasa.crud_room.databinding.DoctorFragmentBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.DoctorWithCategory
import com.serasa.crud_room.utils.ClickOnDoctor
import com.serasa.crud_room.view_activity.DoctorActivity
import com.serasa.crud_room.view_model.CategoryViewModel
import com.serasa.crud_room.view_model.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorFragment : Fragment(R.layout.doctor_fragment), ClickOnDoctor {

    companion object {
        fun newInstance() = DoctorFragment()
    }

    private lateinit var viewModel: DoctorViewModel
    private lateinit var viewModelCategory: CategoryViewModel
    private lateinit var binding: DoctorFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorAdapter
//    private lateinit var repository: AppDatabase
    private var CATEGORY: List<Category> = listOf()
//    private var categoryObject : Category? = null

    private val observerDoctor = Observer<List<DoctorWithCategory>> {
        if (it.size == 0) {
            Snackbar.make(requireView(), "Nobody is here", Snackbar.LENGTH_LONG).show()
        }
        adapter.refresh(it, CATEGORY)
    }

    private val observerCategory = Observer<List<Category>> {
        CATEGORY = it
//        viewModel.fetchDoctors()
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), "Error Error", Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        viewModelCategory.fetchCategories()
        viewModel.fetchDoctors()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        executeComponents()
//        loadCategory()
    }

    fun loadComponents(view: View) {
        binding = DoctorFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        adapter = DoctorAdapter(this)
        recyclerView = binding.recyclerViewDoctor
//        repository = AppDatabase.getDatabase(requireContext())
    }

    fun executeComponents() {


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.doctor.observe(viewLifecycleOwner, observerDoctor)
        viewModelCategory.category.observe(viewLifecycleOwner, observerCategory)

//        viewModel.fetchDoctors()
//        viewModelCategory.fetchCategories()

        binding.floatingActionButtonAddDoctor.setOnClickListener {
            Intent(requireContext(), DoctorActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

//    fun loadCategory() {
//
//        CATEGORY.addAll(repository.categoryDAO().getCategories())
//
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_dropdown_item_1line,
//            CATEGORY
//        )
//        val textView = view!!.findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoryDoctor) as AutoCompleteTextView
//        textView.setAdapter(adapter)
//
//        view!!.findViewById<AutoCompleteTextView>(R.id.autoCompleteCategoryDoctor)
//            .setOnItemClickListener { adapterView, view, i, l ->
//            categoryObject = adapterView.getItemAtPosition(i) as Category
//        }
//    }


    override fun onClickUpdate(doctorWithCategory: DoctorWithCategory) {

    }

    override fun onClickSave(doctorWithCategory: DoctorWithCategory) {
        viewModel.updateDoctor(doctorWithCategory)
        viewModel.fetchDoctors()
    }

    override fun onClcikDelete(doctorWithCategory: DoctorWithCategory) {
            AlertDialog.Builder(context)
                .setTitle("Do you want to delete ${doctorWithCategory.doctor!!.name}?")
                .setMessage("This will delete from Database. Are you sure?")
                .setPositiveButton(R.string.ok){dialog, which ->
                    viewModel.deleteDoctor(doctorWithCategory.doctor)
                    viewModel.fetchDoctors()
                }
                .setNegativeButton(R.string.cancel){dialog,which ->

                }
                .create()
                .show()
    }
}