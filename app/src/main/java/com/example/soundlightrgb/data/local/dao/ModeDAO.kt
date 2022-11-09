package com.example.soundlightrgb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.soundlightrgb.data.local.entity.ModeEntity

@Dao
interface ModeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModes(modes: List<ModeEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateModes(modes: List<ModeEntity>)

    @Query("SELECT * FROM mode")
    suspend fun getModes(): List<ModeEntity>
}