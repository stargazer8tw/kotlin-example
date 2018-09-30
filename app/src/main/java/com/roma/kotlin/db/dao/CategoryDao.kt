package com.roma.kotlin.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Update
import android.arch.persistence.room.Transaction

import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.db.obj.CategoryAndSubCategories
import com.roma.kotlin.db.obj.SubCategory

@Dao
interface CategoryDao {

    companion object{
        const val COL_UID = "uid"
        const val COL_CATE_UID = "category_uid"
        const val COL_NAME = "name"
        const val COL_SEQ = "seq"

        const val TBL_CATEGORY = "category"
        const val TBL_SUBCATEGORY = "subcategory"
    }

    @get:Query("SELECT * FROM $TBL_CATEGORY ORDER BY $COL_SEQ ASC")
    val all: List<Category>

    @get:Query("SELECT * FROM $TBL_SUBCATEGORY ORDER BY $COL_SEQ ASC")
    val subCategories: List<SubCategory>

    @Query("SELECT * FROM $TBL_CATEGORY ORDER BY $COL_SEQ ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Transaction
    @Query("SELECT * FROM $TBL_CATEGORY ORDER BY $COL_SEQ ASC")
    fun getFullCategories(): LiveData<List<CategoryAndSubCategories>>

    @Query("SELECT * FROM $TBL_SUBCATEGORY WHERE $COL_CATE_UID=:uid ORDER BY $COL_SEQ ASC")
    fun getSubCategories(uid: String): LiveData<List<SubCategory>>

    @Query("SELECT * FROM $TBL_CATEGORY WHERE $COL_NAME=:name")
    fun getCategory(name: String):Category

    /**
     * OnConflictStrategy.REPLACE will cause relation data deleted
     * @see https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..%2Findex#4
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subcategory: SubCategory)

    @Delete
    fun delete(subcategory: SubCategory)

    @Update
    fun update(category: Category)

    @Update
    fun update(subcategory: SubCategory)
}