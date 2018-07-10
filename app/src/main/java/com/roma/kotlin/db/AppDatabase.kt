package com.roma.kotlin.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.roma.kotlin.db.dao.CategoryDao
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.db.obj.SubCategory

/**
 * see https://github.com/irontec/android-room-example
 */
@Database(entities = arrayOf(Category::class, SubCategory::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {

        /**
         * The only instance
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

//        fun getInstance(context: Context): AppDatabase =
//                INSTANCE ?: synchronized(this) {
//                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//                }
//
//        private fun buildDatabase(context: Context) =
//                Room.databaseBuilder(context.applicationContext,
//                        AppDatabase::class.java, "roma.db")
//                        .build()
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "example")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE!!
        }
    }

}