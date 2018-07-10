package com.roma.kotlin.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
import com.roma.kotlin.db.obj.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category ORDER BY seq ASC")
    fun getAll(): List<Category>

    // https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..%2Findex#4
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(cate: Category)

}