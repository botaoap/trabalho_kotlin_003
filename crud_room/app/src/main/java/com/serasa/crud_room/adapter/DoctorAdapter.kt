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
import com.serasa.crud_room.databinding.ItemDoctorBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.DoctorWithCategory
import com.serasa.crud_room.utils.ClickOnDoctor


class DoctorAdapter(val onClick: ClickOnDoctor) : RecyclerView.Adapter<ItemDoctorViewHolder>() {

    private val listOfDoctor = mutableListOf<DoctorWithCategory>()
    private var CATEGORY: List<Category> = listOf()
    private lateinit var repository: AppDatabase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDoctorViewHolder {
        repository = AppDatabase.getDatabase(parent.context)
//        loadCategory()

        LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false).apply {
            return ItemDoctorViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemDoctorViewHolder, position: Int) {
        listOfDoctor[position].apply {

            holder.bind(this, CATEGORY, onClick)
        }
    }

    override fun getItemCount(): Int {
        return listOfDoctor.size
    }

    fun refresh(newList: List<DoctorWithCategory>, listCategories: List<Category>) {
        listOfDoctor.clear()
        listOfDoctor.addAll(newList)
        CATEGORY = listCategories
        notifyDataSetChanged()
    }

//    fun loadCategory() {
//        CATEGORY.clear()
//        CATEGORY.addAll(repository.categoryDAO().getCategories())
//    }

}

class ItemDoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemDoctorBinding.bind(itemView)
    private var categoryObject: Category? = null

    fun bind(
        doctorWithCategory: DoctorWithCategory,
        categories: List<Category>,
        onClick: ClickOnDoctor
    ) {
        binding.editTextIdDoctor.setText(doctorWithCategory.doctor?.id.toString())
        binding.editTextNameDoctor.setText(doctorWithCategory.doctor!!.name)

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonDeleteDoctor)
            .setOnClickListener {
                onClick.onClcikDelete(doctorWithCategory)
            }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonUpdateDoctor)
            .setOnClickListener {
                binding.editTextNameDoctor.isEnabled = true
                binding.autoCompleteCategoryDoctor.isEnabled = true
                binding.floatingActionButtonUpdateDoctor.visibility = View.INVISIBLE
                binding.floatingActionButtonSaveDoctor.visibility = View.VISIBLE
                onClick.onClickUpdate(doctorWithCategory)
            }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonSaveDoctor)
            .setOnClickListener {
                binding.editTextNameDoctor.isEnabled = false
                binding.autoCompleteCategoryDoctor.isEnabled = false
                binding.floatingActionButtonUpdateDoctor.visibility = View.VISIBLE
                binding.floatingActionButtonSaveDoctor.visibility = View.INVISIBLE
                doctorWithCategory.doctor.name = binding.editTextNameDoctor.text.toString()
                if (categoryObject != null) {
                    doctorWithCategory.doctor.categoryFk = categoryObject?.id!!
                } else {
                    doctorWithCategory.doctor.categoryFk = doctorWithCategory.category?.id.toString().toLong()
                }
                onClick.onClickSave(doctorWithCategory)
            }

        val adapter = ArrayAdapter(
            itemView.context,
            android.R.layout.simple_dropdown_item_1line,
            categories
        )

        val textView = binding.autoCompleteCategoryDoctor as AutoCompleteTextView
        textView.setAdapter(adapter)

        binding.autoCompleteCategoryDoctor.setOnItemClickListener { adapterView, view, i, l ->
            categoryObject = adapterView.getItemAtPosition(i) as Category
        }

        binding.autoCompleteCategoryDoctor.setText(doctorWithCategory.category?.name, false)
    }

}