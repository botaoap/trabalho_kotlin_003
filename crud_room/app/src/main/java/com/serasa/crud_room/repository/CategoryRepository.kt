package com.serasa.crud_room.repository

import com.serasa.crud_room.database.AppDatabase
import com.serasa.crud_room.database.dao.CategoryDAO
import com.serasa.crud_room.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: AppDatabase
){
    fun fetchCategories(): List<Category> {
        return dao.categoryDAO().getCategories()
    }

    fun fetchCategoryById(id: Long): Category {
        return dao.categoryDAO().getCategoryById(id)
    }

    fun updateCategories(category: Category) {
        dao.categoryDAO().updateCategoryTest(category)
    }

    fun deleteCategories(category: Category) {
        dao.categoryDAO().deleteCategory(category)
    }

    fun insertCategories(category: Category) {
        dao.categoryDAO().insertCategory(category)
    }
}