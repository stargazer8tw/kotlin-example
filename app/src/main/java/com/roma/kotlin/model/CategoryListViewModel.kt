package com.roma.kotlin.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask

import com.roma.kotlin.db.dao.CategoryDao
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.db.obj.CategoryAndSubCategories
import com.roma.kotlin.db.obj.SubCategory

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
    private val fullCategoryList = MediatorLiveData<List<CategoryAndSubCategories>>()

    init {
        val liveCategoryList = categoryDao.getAllCategories()
        val liveFullCategoryList = categoryDao.getFullCategories()
        categoryList.addSource(liveCategoryList, categoryList::setValue)
        fullCategoryList.addSource(liveFullCategoryList, fullCategoryList::setValue)
    }

    fun getCategories() = categoryList

    fun getFullCategories() = fullCategoryList

    fun addCategory(category: Category) {
        addAsynTask(categoryDao).execute(category)
    }

    fun updateCategory(category: Category) {
        updateAsynTask(categoryDao).execute(category)
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

    class updateAsynTask(categoryDao: CategoryDao) : AsyncTask<Category, Void, Void>() {
        private var repo = categoryDao
        override fun doInBackground(vararg params: Category): Void? {
            repo.update(params[0])
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

    fun getSubCategories(item: Category) : LiveData<List<SubCategory>> {
//        val list = MediatorLiveData<List<SubCategory>>()
//        val liveList = categoryDao.getSubCategories(item.uid)
//        list.addSource(liveList, liveList::setValue)
        return categoryDao.getSubCategories(item.uid)
    }

    fun addSubCategory(item: SubCategory) {
        addAsynTaskSC(categoryDao).execute(item)
    }

    fun updateSubCategory(item: SubCategory) {
        updateAsynTaskSC(categoryDao).execute(item)
    }

    fun deleteSubCategory(item: SubCategory) {
        deleteAsynTaskSC(categoryDao).execute(item)
    }

    class addAsynTaskSC(categoryDao: CategoryDao) : AsyncTask<SubCategory, Void, Void>() {
        private var repo = categoryDao
        override fun doInBackground(vararg params: SubCategory): Void? {
            repo.insert(params[0])
            return null
        }
    }

    class updateAsynTaskSC(categoryDao: CategoryDao) : AsyncTask<SubCategory, Void, Void>() {
        private var repo = categoryDao
        override fun doInBackground(vararg params: SubCategory): Void? {
            repo.update(params[0])
            return null
        }
    }

    class deleteAsynTaskSC(categoryDao: CategoryDao) : AsyncTask<SubCategory, Void, Void>() {
        private var repo = categoryDao
        override fun doInBackground(vararg params: SubCategory): Void? {
            repo.delete(params[0])
            return null
        }

    }
}