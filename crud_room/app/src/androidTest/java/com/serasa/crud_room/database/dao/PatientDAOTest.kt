package com.serasa.crud_room.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.model.Patient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PatientDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PatientDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.patientDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun get_all_patient_returns_true() {
        val patient1 = Patient(name = "aa", age = 10, gender = "M")

        dao.insertPatient(patient1)

        val result = dao.getPatients()
        assertThat(result).contains(listOf(patient1))
    }

    @Test
    fun insert_patient_returns_true() {
        val patient1 = Patient(name = "aa", age = 10, gender = "M")
        val patient2 = Patient(name = "bb", age = 11, gender = "F")
        val patient3 = Patient(name = "cc", age = 12, gender = "M")

        dao.insertPatient(patient1)
        dao.insertPatient(patient2)
        dao.insertPatient(patient3)

        val result = dao.getPatients()
        assertThat(result).hasSize(3)
    }

    @Test
    fun delete_patient_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")

        dao.insertPatient(patient1)
        dao.deletePatient(patient1)

        val result = dao.getPatients()
        assertThat(result).hasSize(0)
    }

    @Test
    fun update_patient_returns_true() {
        val patient1 = Patient(id = 1,name = "aa", age = 10, gender = "M")
        val patient2 = Patient(id = 1,name = "aabb", age = 10, gender = "M")
        dao.insertPatient(patient1)
        dao.updatePatientTest(patient2)

        val result = dao.getPatients()
        assertThat(result).isNotEqualTo(patient1)
    }
}