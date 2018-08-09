package com.roma.kotlin.fragments

import android.support.v7.widget.RecyclerView
import com.roma.kotlin.db.obj.Category

/**
 * listener for list crud actions
 * @see https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-6a6f0c422efd
 */
interface ListActionListener<T> {

    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)

    /**
     * @return true when delete record from repository is successful
     */
    fun onDelete(item: T) : Boolean

    /**
     * @return true when reorder records in the repository is successful
     */
    fun onDragComplete(items : List<T>) : Boolean

    fun onItemClick(items : T) : Boolean
}