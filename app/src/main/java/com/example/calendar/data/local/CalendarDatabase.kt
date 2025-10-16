package com.example.calendar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calendar.data.local.dao.EventDao
import com.example.calendar.data.local.entity.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CalendarDatabase : RoomDatabase() {
    
    abstract fun eventDao(): EventDao
    
    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null
        
        fun getDatabase(context: Context): CalendarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "calendar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


