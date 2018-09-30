package com.roma.kotlin.fragments

import android.util.Log
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roma.kotlin.R
import com.roma.kotlin.db.obj.SubCategory
import com.roma.kotlin.fragments.helper.SwipeAndDragHelper
import java.util.Collections
import android.view.MotionEvent
import kotlinx.android.synthetic.main.fragment_subcategory.view.*

/**
 * fallback to use RecyclerView.Adapter instead of ListAdapter
 * @see https://android.devdon.com/archives/113
 */
class SubCategoryRecyclerViewAdapter(val listener : ListActionListener<SubCategory>) : RecyclerView.Adapter<SubCategoryRecyclerViewAdapter.ViewHolder>(),
        SwipeAndDragHelper.ItemMoveSwipeListener {

    private var mList : List<SubCategory> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_subcategory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.apply {
            bind(listener, item)
            itemView.tag = item
            /** @see https://www.techotopia.com/index.php/Kotlin_-_Android_Touch_and_Multi-touch_Event_Handling */
            itemView.image_reorder_subcategory.setOnTouchListener { v: View, m: MotionEvent ->
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

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(newList : List<SubCategory>) {
        // fast simple first insert
        if (mList.isEmpty()) {
            mList = newList
            // notify last, after list is updated
            notifyItemRangeChanged(0, newList.size)
            return
        }
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(mListener: ListActionListener<SubCategory>, item: SubCategory) {
            mView.subcategory_name.text = item.name
            mView.setOnClickListener { mListener.onItemClick(FragmentCategory.ACTION_EDIT_CATEGORY , item) }
            Log.d("add on click listener", "$mView , $item.seq -> $item.name")
        }
    }

    override fun onItemMoved(oldPosition: Int, newPosition: Int) {
        Log.d("move", "$oldPosition -> $newPosition")
        if (oldPosition < newPosition) {
            for (i in oldPosition until newPosition) {
                Collections.swap(mList, i, i + 1)
            }
        } else {
            for (i in oldPosition downTo newPosition + 1) {
                Collections.swap(mList, i, i - 1)
            }
        }
        // reorder
        mList.forEachIndexed { index, category ->
            Log.d("move", "$category.seq -> $index.toLong()")
            category.seq = index.toLong()
        }
        if (listener.onDragComplete(mList)) {
            notifyItemMoved(oldPosition, newPosition)
        }
    }

    override fun onItemSwiped(position: Int) {
        val mod = mList.toMutableList()
        val category = mod.removeAt(position)
        // delete record in repository
        if (listener.onDelete(category)) {
            mList = mod.toList()
            notifyItemRemoved(position)
        }
    }
}
