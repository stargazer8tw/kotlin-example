package com.roma.kotlin.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask

import com.roma.kotlin.db.dao.CategoryDao
import com.roma.kotlin.db.obj.Category

/**
 * @see https://github.com/MarcOliva/Room-Db-with-LiveData-Kotlin
 *
 * use ViewModel instead of androidviewmodel
 * @see https://stackoverflow.com/questions/44148966/androidviewmodel-vs-viewmodel
 * @see https://antonioleiva.com/architecture-components-kotlin/
 * @see https://developer.android.com/topic/libraries/architecture/viewmodel
 * @see https://github.com/googlesamples/android-sunflower
 */
class CategoryListViewModel(private val categoryDao: CategoryDao) : ViewModel() {

    private val categoryList = MediatorLiveData<List<Category>>()

    init {
//        categoryList = categoryDao.getAllCategories()
        val liveCategoryList = categoryDao.getAllCategories()
        categoryList.addSource(liveCategoryList, categoryList::setValue)
    }

    fun getCategories() = categoryList

    fun addCategory(category: Category) {
        addAsynTask(categoryDao).execute(category)
    }

    fun deleteCategory(category: Category) {
        deleteAsynTask(categoryDao).execute(category)
    }

    class addAsynTask(categoryDao: CategoryDao) : AsyncTask<Category, Void, Void>() {
        private var repo = categoryDao
        override fun doInBackground(vararg params: Category): Void? {
            repo.insert(params[0])
            return null
        }

    }

    class deleteAsynTask(categoryDao: CategoryDao) : AsyncTask<Category, Void, Void>() {
        private var repo = categoryDao
        override fun doInBackground(vararg params: Category): Void? {
            repo.delete(params[0])
            return null
        }

    }
}