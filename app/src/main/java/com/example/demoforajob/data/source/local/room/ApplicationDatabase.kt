package com.example.demoforajob.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demoforajob.data.source.local.room.dao.MainDao
import com.example.demoforajob.data.source.local.room.entity.ItemDataEntity

@Database(entities = [ItemDataEntity::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun getDao(): MainDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun init(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ApplicationDatabase::class.java,
                    "RickandMorty_db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}