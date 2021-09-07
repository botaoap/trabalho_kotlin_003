package com.serasa.crud_room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.serasa.crud_room.R
import com.serasa.crud_room.databinding.ItemPatientBinding
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Patient
import com.serasa.crud_room.utils.ClickOnPatient


class PatientAdapter(val onClick: ClickOnPatient): RecyclerView.Adapter<ItemPatientViewHolder>() {

    private val listOfPatient = mutableListOf<Patient>()
    private var GENDER: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPatientViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false).apply {
            return ItemPatientViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemPatientViewHolder, position: Int) {
        listOfPatient[position].apply {
            holder.bind(this, GENDER, onClick)
        }
    }

    override fun getItemCount(): Int {
        return listOfPatient.size
    }

    fun refresh(newList: List<Patient>, lisfOfGender: List<String>) {
        listOfPatient.clear()
        listOfPatient.addAll(newList)
        GENDER = lisfOfGender
        notifyDataSetChanged()

    }
}

class ItemPatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPatientBinding.bind(itemView)
    private var genderObject: String? = null

    fun bind(patient: Patient,gender: List<String>, onClick: ClickOnPatient) {
        binding.autoCompleteGender.setText(patient.gender)

        val adapter = ArrayAdapter(
            itemView.context,
            android.R.layout.simple_dropdown_item_1line,
            gender
        )

        val textView = binding.autoCompleteGender as AutoCompleteTextView
        textView.setAdapter(adapter)

        binding.autoCompleteGender.setOnItemClickListener { adapterView, view, i, l ->
            genderObject = adapterView.getItemAtPosition(i) as String
        }

        binding.editTextId.setText(patient.id.toString())
        binding.editTextName.setText(patient.name)
        binding.editTextAge.setText(patient.age.toString())

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonEdit).setOnClickListener {
            binding.editTextName.isEnabled = true
            binding.autoCompleteGender.isEnabled = true
            binding.editTextAge.isEnabled = true
            binding.floatingActionButtonSave.visibility = VISIBLE
            binding.floatingActionButtonEdit.visibility = INVISIBLE
            onClick.onClickUpdate(patient)
        }
        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonSave).setOnClickListener {
            binding.editTextName.isEnabled = false
            binding.autoCompleteGender.isEnabled = false
            binding.editTextAge.isEnabled = false
            binding.floatingActionButtonSave.visibility = INVISIBLE
            binding.floatingActionButtonEdit.visibility = VISIBLE
            patient.name = binding.editTextName.text.toString()
            if (genderObject != null) {
                patient.gender = genderObject!!
            } else {
                patient.gender = patient.gender
            }
            patient.age = binding.editTextAge.text.toString().toInt()
            onClick.onClickSave(patient)

        }

        itemView.findViewById<FloatingActionButton>(R.id.floating_action_buttonDelete).setOnClickListener {
            onClick.onClcikDelete(patient)
        }

    }
}