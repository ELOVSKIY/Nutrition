package com.helicoptera.nutrition.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.helicoptera.nutrition.data.db.converter.DateConverter
import com.helicoptera.nutrition.data.db.dao.MealDao
import com.helicoptera.nutrition.data.db.entity.MealEntity

@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract val mealDao: MealDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: MealDatabase

        fun getInstance(context: Context): MealDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(MealDatabase::class.java) {
                    if (!::INSTANCE.isInitialized) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, MealDatabase::class.java, "meal"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}



