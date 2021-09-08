package com.serasa.crud_room.view_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.serasa.crud_room.R
import com.serasa.crud_room.databinding.MainActivityBinding
import com.serasa.crud_room.utils.replaceView
import com.serasa.crud_room.view.AppointmentFragment
import com.serasa.crud_room.view.DoctorFragment
import com.serasa.crud_room.view.CategoryFragment
import com.serasa.crud_room.view.PatientFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.person -> {
                    // Respond to navigation item 1 click
                    replaceView(PatientFragment.newInstance())
                    true
                }
                R.id.category -> {
                    // Respond to navigation item 2 click
                    replaceView(CategoryFragment.newInstance())
                    true
                }
                R.id.doctor -> {
                    // Respond to navigation item 3 click
                    replaceView(DoctorFragment.newInstance())
                    true
                }
                R.id.appointment -> {
                    // Respond to navigation item 4 click
                    replaceView(AppointmentFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }
}