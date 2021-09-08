package com.serasa.crud_room.view

import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.serasa.crud_room.R
import com.serasa.crud_room.adapter.CategoryAdapter
import com.serasa.crud_room.database.dao.CategoryDAO
import com.serasa.crud_room.databinding.CategoryFragmentBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.repository.CategoryRepository
import com.serasa.crud_room.utils.ClickOnCategory
import com.serasa.crud_room.view_activity.CategoryActivity
import com.serasa.crud_room.view_activity.PatientActivity
import com.serasa.crud_room.view_model.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import java.util.EnumSet.of
import java.util.List.of
import java.util.Optional.of

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.category_fragment), ClickOnCategory {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: CategoryFragmentBinding
    private lateinit var adapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView

    private val observerCategory = Observer<List<Category>> {
        if (it.size == 0) {
            Snackbar.make(requireView(), "Nobody is here", Snackbar.LENGTH_LONG).show()
        }
        adapter.refresh(it)
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), "Error Error", Snackbar.LENGTH_LONG).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        executeComponents()
    }

    fun loadComponents(view: View) {
        binding = CategoryFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        recyclerView = binding.recyclerViewCategory
        adapter = CategoryAdapter(this)

        viewModel.fetchCategories()


        viewModel.category.observe(viewLifecycleOwner, observerCategory)
        viewModel.error.observe(viewLifecycleOwner, observerError)

    }

    fun executeComponents() {

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.fetchCategories()

        binding.floatingActionButtonAddCategory.setOnClickListener {
            Intent(requireContext(), CategoryActivity::class.java).apply {
                startActivity(this)
            }
        }
    }


    override fun onClickUpdate(category: Category) {

    }

    override fun onClickSave(category: Category) {
        viewModel.updateCategories(category)
        viewModel.fetchCategories()
    }

    override fun onClcikDelete(category: Category) {
        AlertDialog.Builder(context)
            .setTitle("Do you want to delete ${category.name}?")
            .setMessage("This will delete from Database. Are you sure?")
            .setPositiveButton(R.string.ok){dialog, which ->
                viewModel.deleteCategories(category)
                viewModel.fetchCategories()
            }
            .setNegativeButton(R.string.cancel){dialog,which ->

            }
            .create()
            .show()
    }
}