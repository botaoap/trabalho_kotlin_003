package com.serasa.crud_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.serasa.crud_room.database.dao.AppointmentDAO
import com.serasa.crud_room.database.dao.CategoryDAO
import com.serasa.crud_room.database.dao.DoctorDAO
import com.serasa.crud_room.database.dao.PatientDAO
import com.serasa.crud_room.model.Appointment
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.model.Patient

@Database(
    entities = [Patient::class, Doctor::class, Category::class, Appointment::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDAO(): PatientDAO
    abstract fun doctorDAO(): DoctorDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun appointmentDAO(): AppointmentDAO

    companion object {

        fun getDatabase(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "projetct_crud_room"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}