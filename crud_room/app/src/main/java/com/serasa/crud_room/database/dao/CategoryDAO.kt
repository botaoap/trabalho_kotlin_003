package com.serasa.crud_room.database.dao

import androidx.room.*
import com.serasa.crud_room.model.Category

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM Category")
    fun getCategories() : List<Category>

    @Query("SELECT * FROM Category WHERE cat_id = :id")
    fun getCategoryById(id: Long): Category

    @Insert
    fun insertCategory(insert: Category)

    @Delete
    fun deleteCategory(delete: Category)

    @Update
    fun updateCategoryTest(category: Category)
}