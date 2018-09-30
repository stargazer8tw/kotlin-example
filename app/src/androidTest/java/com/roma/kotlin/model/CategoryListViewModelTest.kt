package com.roma.kotlin.model

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.roma.kotlin.db.AppDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * https://blog.philipphauer.de/best-practices-unit-testing-kotlin/
 */
@RunWith(AndroidJUnit4::class)
class CategoryListViewModelTest {

    private lateinit var vm: CategoryListViewModel

    @Before
    fun setup() {
        // using an in-memory database because the information stored here disappears after test
//        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

//        vm = CategoryListViewModel(testComponent)
//        database = AppDatabase.getInstance(InstrumentationRegistry.getTargetContext())
    }

    @After
    fun tearDown() {
//        database.clearAllTables()
//        database.close()
    }

    @Test
    fun insertAndGetCategory() {
        // When inserting a new subcategory in the data source
//        database.categoryDao().insert(CategoryDaoTest.CATEGORY)
//
//        val list = database.categoryDao().all
//        Assert.assertNotNull(list)
//        Assert.assertEquals(CategoryDaoTest.CATEGORY.name, list.get(0).name)
    }
}