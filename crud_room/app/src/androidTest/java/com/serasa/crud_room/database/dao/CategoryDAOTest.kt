package com.serasa.crud_room.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.model.Category
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CategoryDAOTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: CategoryDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.categoryDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun get_all_category_returns_true() {
        val category1 = Category(name = "Medico1")
        val category2 = Category(name = "Medico2")
        val category3 = Category(name = "Medico3")

        dao.insertCategory(category1)
        dao.insertCategory(category2)
        dao.insertCategory(category3)

        val result = dao.getCategories()
        assertThat(result).contains(category1)
    }

    @Test
    fun insert_category_returns_true() {
        val category1 = Category(name = "Medico1")
        val category2 = Category(name = "Medico2")
        val category3 = Category(name = "Medico3")

        dao.insertCategory(category1)
        dao.insertCategory(category2)
        dao.insertCategory(category3)

        val result = dao.getCategories()
        assertThat(result).hasSize(3)
    }

    @Test
    fun delete_category_returns_true() {
        val category1 = Category(id = 1,name = "Medico1")

        dao.insertCategory(category1)
        dao.deleteCategory(category1)

        val result = dao.getCategories()
        assertThat(result).hasSize(0)
    }

    @Test
    fun update_category_returns_true() {
        val category1 = Category(id = 1,name = "Medico1")
        val category2 = Category(id = 1,name = "Medicado")
        dao.insertCategory(category1)
        dao.updateCategoryTest(category2)

        val result = dao.getCategories()
        assertThat(result).isNotEqualTo(category1)
    }


}