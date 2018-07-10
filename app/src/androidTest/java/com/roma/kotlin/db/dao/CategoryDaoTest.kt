package com.roma.kotlin.db.dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

import com.roma.kotlin.db.AppDatabase
import com.roma.kotlin.db.obj.Category

/**
 * Test the implementation of [CategoryDao]
 */
@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {

    private lateinit var database: AppDatabase

    @Before fun setup() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        // see https://medium.com/@chandilsachin/room-with-unit-test-in-kotlin-4ad31a39a291
//        database = AppDatabase.getInstance(InstrumentationRegistry.getTargetContext())
    }

    @After fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun insertAndGetCategory() {
        // When inserting a new category in the data source
        database.categoryDao().insert(CATEGORY)

        val list = database.categoryDao().getAll()
        assertNotNull(list)
        assertEquals(CATEGORY.name, list.get(0).name)
    }

    @Test
    fun updateAndGetCategory() {
        // When inserting a new category in the data source
        database.categoryDao().insert(CATEGORY)

        // When we are updating the name of the user
        val updatedCategory = Category(CATEGORY.uid, "new food", 0)
        database.categoryDao().insert(updatedCategory)

        val list = database.categoryDao().getAll()
        assertNotNull(list)
        assertEquals(updatedCategory.name, list.get(0).name)
    }

    @Test
    fun deleteAndGetCategory() {
        // When inserting a new category in the data source
        database.categoryDao().insert(CATEGORY)

        //When we are deleting all category
        database.categoryDao().delete(CATEGORY)

        val list = database.categoryDao().getAll()
        assertNotNull(list)
        assertEquals(0, list.size)
    }

    companion object {
        private val CATEGORY = Category(0, "food", 0)
    }
}