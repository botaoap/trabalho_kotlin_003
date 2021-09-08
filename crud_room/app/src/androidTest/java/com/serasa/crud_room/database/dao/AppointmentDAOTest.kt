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

        val appointment1 = Appointment(id = 1,patFk = patient1.id, doctor1.id)

        dao.insertAppointment(appointment1)

        val result = dao.getAppointments()
        assertThat(result).isEqualTo(appointment1)
    }

    @Test
    fun insert_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        val appointment1 = Appointment(id = 1,patFk = 1, docFk = 1)

        dao.insertAppointment(appointment1)

        val result = dao.getAppointments()
        assertThat(result).hasSize(1)
    }

    @Test
    fun delete_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        val appointment1 = Appointment(id = 1,patFk = 1, docFk = 1)

        dao.deleteAppointment(appointment1)

        val result = dao.getAppointments()
        assertThat(result).hasSize(0)
    }

    @Test
    fun update_appointment_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)
        val appointment1 = Appointment(id = 1,patFk = 1, docFk = 1)
        val appointment2 = Appointment(id = 1,patFk = 2, docFk = 2)

        dao.insertAppointment(appointment1)

        dao.updateAppointmentTest(appointment2)

        val result = dao.getAppointments()
        assertThat(result).isNotEqualTo(appointment1)
    }
}