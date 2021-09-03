package com.serasa.crud_room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pat_id")
    val id: Long = 0,
    @ColumnInfo(name = "pat_name")
    val name: String,
    @ColumnInfo(name = "pat_age")
    val age: Int,
    @ColumnInfo(name = "pat_gender")
    val gender: String
)