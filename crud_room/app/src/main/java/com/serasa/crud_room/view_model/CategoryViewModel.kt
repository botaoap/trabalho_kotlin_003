package com.serasa.crud_room.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serasa.crud_room.model.Category
import com.serasa.crud_room.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    val repository: CategoryRepository
): ViewModel() {

    private val _category = MutableLiveData<List<Category>>()
    var category: LiveData<List<Category>> = _category

    private val _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    fun fetchCategories() {
        _category.value = repository.fetchCategories()
    }

    fun updateCategories(category: Category) {
        repository.updateCategories(category)
    }

    fun deleteCategories(category: Category) {
        repository.deleteCategories(category)
    }

    fun insertCategories(category: Category) {
        repository.insertCategories(category)
    }

}