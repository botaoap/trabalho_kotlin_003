package com.serasa.crud_room.model

import androidx.room.*

@Entity
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "doc_id")
    val id: Long = 0,
    @ColumnInfo(name = "doc_name")
    var name: String,

    var categoryFk: Long
)

data class DoctorWithCategory(
    @Embedded
    val doctor: Doctor?,
    @Relation(
        parentColumn = "categoryFk",
        entityColumn = "cat_id"
    )
    val category: Category?
){
    override fun toString(): String {
        return doctor?.name!!
    }
}