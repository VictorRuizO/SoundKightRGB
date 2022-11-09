package com.example.soundlightrgb.di

import android.content.Context
import androidx.room.Room
import com.example.soundlightrgb.data.local.ModeRoomDB
import com.example.soundlightrgb.data.local.dao.ModeDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
 class RoomModule {

    @Provides
    fun provideModeDAO(modeRoomDB: ModeRoomDB): ModeDAO {
         return modeRoomDB.modeDao()
    }

    @Provides
    @Singleton
    fun provideModeRoomDB(@ApplicationContext appContext: Context): ModeRoomDB {
        return Room.databaseBuilder(
            appContext,
            ModeRoomDB::class.java,
            "mode_database"
        ).build()
    }
}