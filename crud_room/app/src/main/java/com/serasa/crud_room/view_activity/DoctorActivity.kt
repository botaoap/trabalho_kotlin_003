package com.serasa.crud_room.view_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.serasa.crud_room.databinding.ActivityDoctorBinding
import com.serasa.crud_room.view_model.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.repository.CategoryRepository
import com.serasa.crud_room.view_model.CategoryViewModel


@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorBinding
    private lateinit var viewModel: DoctorViewModel
    private lateinit var repository: AppDatabase
    private val CATEGORY: MutableList<Category> = mutableListOf()
    private var categoryObject : Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadComponents()
        loadCategory()
        executeComponents()
    }

    fun loadComponents() {
        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        repository = AppDatabase.getDatabase(this)
    }

    fun loadCategory() {

        CATEGORY.addAll(repository.categoryDAO().getCategories())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            CATEGORY
        )
        val textView = binding.autoCompleteCategory as AutoCompleteTextView
        textView.setAdapter(adapter)

        binding.autoCompleteCategory.setOnItemClickListener { adapterView, view, i, l ->
            categoryObject = adapterView.getItemAtPosition(i) as Category
        }
    }

    fun executeComponents() {
        binding.buttonCancel.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.buttonSave.setOnClickListener {
            viewModel.insertDoctor(
                doctor = Doctor(
                    name = binding.editTextNameAddDoctor.text.toString(),
                    categoryFk = categoryObject!!.id
                )
            )
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}