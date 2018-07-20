package com.roma.kotlin.adapters

import android.support.v7.util.DiffUtil
import com.roma.kotlin.db.obj.Category

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>(){

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}