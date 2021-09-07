package com.serasa.crud_room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.serasa.crud_room.R
import com.serasa.crud_room.databinding.ItemCategoryBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.utils.ClickOnCategory

class CategoryAdapter(val onClick: ClickOnCategory) : RecyclerView.Adapter<ItemCategoryViewHolder>() {

    private val listOfCategory = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false).apply {
            return ItemCategoryViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        listOfCategory[position].apply {
            holder.bind(this, onClick)
        }
    }

    override fun getItemCount(): Int {
        return listOfCategory.size
    }

    fun refresh(newlist: List<Category>) {
        listOfCategory.clear()
        listOfCategory.addAll(newlist)
        notifyDataSetChanged()
    }
}

class ItemCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding = ItemCategoryBinding.bind(itemView)

    fun bind(category: Category, onClick: ClickOnCategory) {
        binding.editTextIdCategory.setText(category.id.toString())
        binding.editTextNameCategory.setText(category.name)

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonDeleteCategory).setOnClickListener {
            onClick.onClcikDelete(category)
        }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonUpdateCategory).setOnClickListener {
            binding.editTextNameCategory.isEnabled = true
            binding.floatingActionButtonUpdateCategory.visibility = INVISIBLE
            binding.floatingActionButtonSaveCategory.visibility = VISIBLE
            onClick.onClickUpdate(category)
        }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonSaveCategory).setOnClickListener {
            binding.editTextNameCategory.isEnabled = false
            binding.floatingActionButtonUpdateCategory.visibility = VISIBLE
            binding.floatingActionButtonSaveCategory.visibility = INVISIBLE
            category.name = binding.editTextNameCategory.text.toString()
            onClick.onClickSave(category)
        }

    }
}