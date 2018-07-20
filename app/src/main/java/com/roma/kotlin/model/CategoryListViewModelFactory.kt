package com.roma.kotlin.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.roma.kotlin.db.dao.CategoryDao

/**
 * @see https://github.com/googlesamples/android-sunflower/blob/master/app/src/main/java/com/google/samples/apps/sunflower/viewmodels/PlantListViewModelFactory.kt
 */
class CategoryListViewModelFactory(
        private val repository: CategoryDao
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = CategoryListViewModel(repository) as T
}