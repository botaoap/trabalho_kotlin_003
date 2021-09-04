package com.serasa.crud_room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.serasa.crud_room.R
import com.serasa.crud_room.databinding.ItemPatientBinding
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.utils.ClickOnList


class PatientAdapter(val onClick: ClickOnList): RecyclerView.Adapter<ItemPatientViewHolder>() {

    private val listOfPatient = mutableListOf<Patient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPatientViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false).apply {
            return ItemPatientViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemPatientViewHolder, position: Int) {
        listOfPatient[position].apply {
            holder.bind(this, onClick)
        }
    }

    override fun getItemCount(): Int {
        return listOfPatient.size
    }

    fun refresh(newList: List<Patient>) {
        listOfPatient.clear()
        listOfPatient.addAll(newList)
        notifyDataSetChanged()

    }
}

class ItemPatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPatientBinding.bind(itemView)

    fun bind(patient: Patient, onClick: ClickOnList) {

        binding.editTextId.setText(patient.id.toString())
        binding.editTextName.setText(patient.name)
        binding.editTextGender.setText(patient.gender)
        binding.editTextAge.setText(patient.age.toString())

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonEdit).setOnClickListener {
            binding.editTextName.isEnabled = true
            binding.editTextGender.isEnabled = true
            binding.editTextAge.isEnabled = true
            binding.floatingActionButtonSave.visibility = VISIBLE
            binding.floatingActionButtonEdit.visibility = INVISIBLE
            onClick.onClickEdit(patient)
        }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonSave).setOnClickListener {
            binding.editTextName.isEnabled = false
            binding.editTextGender.isEnabled = false
            binding.editTextAge.isEnabled = false
            binding.floatingActionButtonSave.visibility = INVISIBLE
            binding.floatingActionButtonEdit.visibility = VISIBLE
            patient.name = binding.editTextName.text.toString()
            patient.gender = binding.editTextGender.text.toString()
            patient.age = binding.editTextAge.text.toString().toInt()
            onClick.onClickSave(patient)

        }

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonDelete).setOnClickListener {
            onClick.onClcikDelete(patient)
        }

    }
}