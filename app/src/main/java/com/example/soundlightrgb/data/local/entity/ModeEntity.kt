package com.example.soundlightrgb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mode")
class ModeEntity (
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "value") val value: String
)