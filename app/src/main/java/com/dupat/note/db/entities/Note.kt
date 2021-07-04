package com.dupat.note.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Int,

    val priority: Int,

    var title: String,

    var description: String,

    var created_at: Date,

    var updated_at: Date?

)
