package com.serasa.crud_room.view_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.serasa.crud_room.R
import com.serasa.crud_room.databinding.ActivityCategoryBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.view.CategoryFragment
import com.serasa.crud_room.view_model.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadComponents()
        executeComponents()
    }

    fun loadComponents() {
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
    }

    fun executeComponents() {
        binding.buttonCancelCategory.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.buttonSaveCategory.setOnClickListener {
            viewModel.insertCategories(
                category = Category(
                    name = binding.editTextNameAddCategory.text.toString()
                )
            )

            Intent(this, CategoryFragment::class.java).apply {
                startActivity(this)
            }
        }
    }
}