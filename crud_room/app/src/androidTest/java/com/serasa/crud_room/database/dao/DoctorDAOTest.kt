package com.serasa.crud_room.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.model.Doctor
import com.serasa.crud_room.model.DoctorWithCategory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class DoctorDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: DoctorDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.doctorDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun get_all_doctor_returns_true() {
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        dao.insertDoctor(doctor1)

        val result = dao.getDoctor()
        assertThat(result).contains(doctor1.name)
    }

    @Test
    fun insert_doctor_returns_true() {
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        dao.insertDoctor(doctor1)

        val result = dao.getDoctor()
        assertThat(result).hasSize(1)
    }

    @Test
    fun delete_doctor_returns_true() {
        val category1 = Category(id = 1, name = "Pediatra")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)

        dao.deleteDoctor(doctor1)

        val result = dao.getDoctor()
        assertThat(result).hasSize(0)
    }

    @Test
    fun update_doctor_returns_true() {
        val category1 = Category(id = 1, name = "Pediatra")
        val category2 = Category(id = 2, name = "PPPP")
        val doctor1 = Doctor(id = 1, name = "Medico1", categoryFk = category1.id)
        val doctor2 = Doctor(id = 1, name = "Medicadinho", categoryFk = category2.id)

        dao.insertDoctor(doctor1)
        dao.updateDoctorTest(doctor2)

        val result = dao.getDoctor()
        assertThat(result).isNotEqualTo(doctor1)
    }

}