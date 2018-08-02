package com.roma.kotlin.fragments

import android.content.Context
import android.util.Log
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roma.kotlin.R
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.fragments.helper.SwipeAndDragHelper
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.Collections
import android.view.MotionEvent

/**
 * fallback to use RecyclerView.Adapter instead of ListAdapter
 * @see https://android.devdon.com/archives/113
 */
class CategoryRecyclerViewAdapter(val listener : OnStartDragListener) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>(),
        SwipeAndDragHelper.ItemMoveSwipeListener {

    private var mList : List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = mList[position]
        holder.apply {
            bind(createOnClickListener(category.uid), category)
            itemView.tag = category
            /** @see https://www.techotopia.com/index.php/Kotlin_-_Android_Touch_and_Multi-touch_Event_Handling */
            itemView.image_reorder_category.setOnTouchListener { v: View, m: MotionEvent ->
                val action = m.actionMasked
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        listener.onStartDrag(holder)
                    }
                }
                true
            }
        }
    }

    private fun createOnClickListener(uid: Long): View.OnClickListener {
        return View.OnClickListener {
//            val direction = PlantListFragmentDirections.ActionPlantListFragmentToPlantDetailFragment(plantId)
//            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    fun updateList(newList : List<Category>) {
        // fast simple first insert
        if (mList.isEmpty()) {
            mList = newList
            // notify last, after list is updated
            notifyItemRangeChanged(0, newList.size)
            return
        }
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(listener: View.OnClickListener, item: Category) {
            mView.category_name.text = item.name
            mView.setOnClickListener { listener }
        }
    }

    /**
     * @see https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-6a6f0c422efd
     */
    interface OnStartDragListener {

        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)

        /**
         * @return true when delete record from repository is successful
         */
        fun onDelete(category: Category) : Boolean
        
    }

    override fun onItemMoved(oldPosition: Int, newPosition: Int) {
        Log.i("move", "$oldPosition -> $newPosition")
        if (oldPosition < newPosition) {
            for (i in oldPosition until newPosition) {
                Collections.swap(mList, i, i + 1)
            }
        } else {
            for (i in oldPosition downTo newPosition + 1) {
                Collections.swap(mList, i, i - 1)
            }
        }
        // TODO reorder
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun onItemSwiped(position: Int) {
        val mod = mList.toMutableList() //mutableListOf(mList)
        val category = mod.removeAt(position)
        // TODO delete
        if (listener.onDelete(category)) {
            mList = mod.toList()
            notifyItemRemoved(position)
        }
    }
}
