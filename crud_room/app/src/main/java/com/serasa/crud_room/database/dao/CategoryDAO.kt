package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.Category

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM Category")
    fun getCategories() : List<Category>

    @Insert
    fun insertCategory(insert: Category)

    @Delete
    fun deleteCategory(delete: Category)

    @Update
    fun updateCategoryTest(category: Category)
}