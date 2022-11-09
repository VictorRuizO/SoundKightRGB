package com.example.soundlightrgb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.soundlightrgb.data.local.dao.ModeDAO
import com.example.soundlightrgb.data.local.entity.ModeEntity

@Database(entities = arrayOf(ModeEntity::class), version = 1, exportSchema = false)
abstract class ModeRoomDB: RoomDatabase() {
    abstract fun modeDao(): ModeDAO
}