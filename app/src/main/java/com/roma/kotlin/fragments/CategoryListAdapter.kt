package com.roma.kotlin.fragments

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.MotionEvent
import android.widget.TextView
import com.roma.kotlin.R

import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.adapters.CategoryDiffCallback
import kotlinx.android.synthetic.main.fragment_category.view.*
import com.roma.kotlin.fragments.helper.SwipeAndDragHelper
import com.roma.kotlin.fragments.helper.SwipeAndDragHelper.ItemMoveSwipeListener

/**
 * @see https://github.com/googlesamples/android-sunflower/blob/master/app/src/main/java/com/google/samples/apps/sunflower/adapters/PlantAdapter.kt
 */
class CategoryListAdapter : ListAdapter<Category, CategoryListAdapter.ViewHolder>(CategoryDiffCallback()),
        SwipeAndDragHelper.ItemMoveSwipeListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.apply {
            bind(createOnClickListener(category.uid), category)
            itemView.tag = category
        }
    }

    private fun createOnClickListener(uid: Long): View.OnClickListener {
        return View.OnClickListener {
//            val direction = PlantListFragmentDirections.ActionPlantListFragmentToPlantDetailFragment(plantId)
//            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(listener: View.OnClickListener, item: Category) {
            mView.category_name.text = item.name
            mView.setOnClickListener { listener }
        }
    }



    override fun onItemMoved(oldPosition: Int, newPosition: Int) {
        // TODO reorder logic
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun onItemSwiped(position: Int) {
        // TODO delete logic
        notifyItemRemoved(position)
    }
}
