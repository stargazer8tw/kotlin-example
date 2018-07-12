package com.roma.kotlin.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import com.roma.kotlin.db.AppDatabase
import com.roma.kotlin.db.obj.Category

/**
 * see https://github.com/MarcOliva/Room-Db-with-LiveData-Kotlin
 */
class CategoryListViewModel(application: Application) : AndroidViewModel(application) {
    var listCategory: LiveData<List<Category>>
    private val appDb: AppDatabase

    init {
        appDb = AppDatabase.getInstance(this.getApplication())
        listCategory = appDb.categoryDao().getAllCategories()
    }

    fun getAllListCategory(): LiveData<List<Category>> {
        return listCategory
    }

    fun addCategory(category: Category) {
        addAsynTask(appDb).execute(category)
    }

    class addAsynTask(db: AppDatabase) : AsyncTask<Category, Void, Void>() {
        private var appDb = db
        override fun doInBackground(vararg params: Category): Void? {
            appDb.categoryDao().insert(params[0])
            return null
        }

    }

}