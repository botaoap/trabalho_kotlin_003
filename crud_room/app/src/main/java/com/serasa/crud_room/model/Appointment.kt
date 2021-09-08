package com.serasa.crud_room.model

import androidx.room.*

@Entity
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "appoint_id")
    var id: Long = 0,

    var patFk: Long,

    var docFk: Long
)

data class AppointmentWithRelations(
    @Embedded
    val appointment: Appointment?,
    @Relation(
        parentColumn = "docFk",
        entityColumn = "doc_id"
    )
    val doctor: Doctor?,

    @Relation(
        parentColumn = "patFk",
        entityColumn = "pat_id"
    )
    val patient: Patient?
)