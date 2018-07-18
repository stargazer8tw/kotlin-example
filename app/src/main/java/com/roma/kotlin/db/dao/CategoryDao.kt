package com.roma.kotlin.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete

import com.roma.kotlin.db.obj.Category

@Dao
interface CategoryDao {

    companion object{
        const val COL_UID = "uid"
        const val COL_NAME = "name"
        const val COL_SEQ = "seq"

        const val TBL_CATEGORY = "category"
    }

    @get:Query("SELECT * FROM $TBL_CATEGORY ORDER BY $COL_SEQ ASC")
    val all: List<Category>

    @Query("SELECT * FROM $TBL_CATEGORY ORDER BY $COL_SEQ ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM $TBL_CATEGORY WHERE $COL_NAME=:name")
    fun getCategory(name: String):Category

    // https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..%2Findex#4
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

}