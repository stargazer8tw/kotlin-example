package com.roma.kotlin.utils

import android.content.Context
import com.roma.kotlin.db.AppDatabase
import com.roma.kotlin.db.dao.CategoryDao
import com.roma.kotlin.model.CategoryListViewModelFactory

object InjectorUtils {

    private fun getAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    private fun getCategoryDao(context: Context): CategoryDao {
        return AppDatabase.getInstance(context).categoryDao()
    }

    fun provideCategoryListViewModelFactory(context: Context): CategoryListViewModelFactory {
        val repository = getCategoryDao(context)
        return CategoryListViewModelFactory(repository)
    }
}
