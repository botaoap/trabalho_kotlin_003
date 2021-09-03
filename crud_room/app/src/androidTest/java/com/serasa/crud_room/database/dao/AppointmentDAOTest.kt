package com.serasa.crud_room.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.model.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppointmentDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: AppointmentDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.appointmentDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun get_all_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        val appointment1 = Appointment(id = 1,patFk = 1, 1)
        val appointmentWithRelations =
            AppointmentWithRelations(
                appointment = appointment1,
                doctor = doctor1,
                patient = patient1
            )
        dao.insert(appointmentWithRelations)
        val getAll = dao.getAppointments()

        val result = dao.getAppointments()
        assertThat(result).isEqualTo(getAll)
    }

    @Test
    fun insert_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        val appointment1 = Appointment(id = 1,patFk = 1, docFk = 1)
        val appointmentWithRelations =
            AppointmentWithRelations(
                appointment = appointment1,
                doctor = doctor1,
                patient = patient1
            )
        dao.insert(appointmentWithRelations)

        val result = dao.getAppointments()
        assertThat(result).hasSize(1)
    }

    @Test
    fun delete_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        val appointment1 = Appointment(id = 1,patFk = 1, docFk = 1)
        val appointmentWithRelations =
            AppointmentWithRelations(
                appointment = appointment1,
                doctor = doctor1,
                patient = patient1
            )
        dao.deleteAppointment(appointmentWithRelations)

        val result = dao.getAppointments()
        assertThat(result).hasSize(0)
    }

    @Test
    fun update_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)
        val appointment1 = Appointment(id = 1,patFk = 1, docFk = 1)
        val appointmentWithRelations =
            AppointmentWithRelations(
                appointment = appointment1,
                doctor = doctor1,
                patient = patient1
            )
        dao.insert(appointmentWithRelations)

//        val patient2 = Patient(id = 2,name = "aa", age = 10, gender = "M")
//        val category2 = Category(id = 2, name = "Pediatra")
//        val doctor2 = Doctor(id = 2, name = "Medico1", categoryFk = category2.id)
//        val appointment2 = Appointment(id = 2,patFk = 1, docFk = 1)
//        val appointmentWithRelations2 =
//            AppointmentWithRelations(
//                appointment = appointment2,
//                doctor = doctor2,
//                patient = patient2
//            )
//
//        dao.insert(appointmentWithRelations2)

        dao.updateAppointment(idAppoint = 1, updateDoc = 2, updatePat = 2)

        val result = dao.getAppointments()
        assertThat(result).isNotEqualTo(appointmentWithRelations)
    }
}