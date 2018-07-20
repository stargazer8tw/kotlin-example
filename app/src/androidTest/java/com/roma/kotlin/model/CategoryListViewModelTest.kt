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

    private lateinit val vm: CategoryListViewModel

    @Before
    fun setup() {
        // using an in-memory database because the information stored here disappears after test
//        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        // see https://medium.com/@chandilsachin/room-with-unit-test-in-kotlin-4ad31a39a291
        vm = CategoryListViewModel(testComponent)
        database = AppDatabase.getInstance(InstrumentationRegistry.getTargetContext())
    }

    @After
    fun tearDown() {
        database.clearAllTables()
//        database.close()
    }

    @Test
    fun insertAndGetCategory() {
        // When inserting a new category in the data source
        database.categoryDao().insert(CategoryDaoTest.CATEGORY)

        val list = database.categoryDao().all
        Assert.assertNotNull(list)
        Assert.assertEquals(CategoryDaoTest.CATEGORY.name, list.get(0).name)
    }
    @Test
    fun testGetAll() {
        val response = arrayListOf(CategoryListViewModel())
        whenever(cryptoListUseCases.getCryptoListBy(anyInt()))
                .thenReturn(Single.just(response))

        viewmodel.stateLiveData.observeForever(observerState)

        viewmodel.updateCryptoList()
        val firstPage = 0
        verify(cryptoListUseCases).getCryptoListBy(firstPage)

        val argumentCaptor =
                ArgumentCaptor.forClass(CryptoListState::class.java)
        val expectedLoadingState =
                LoadingState(firstPage, false, emptyList())
        val expectedDefaultState =
                DefaultState(firstPage+1, true, response)
        argumentCaptor.run {
            verify(observerState, times(3)).onChanged(capture())
            val (initialState, loadingState, defaultState) = allValues
            assertEquals(loadingState, expectedLoadingState)
            assertEquals(defaultState, expectedDefaultState)
        }
    }
}