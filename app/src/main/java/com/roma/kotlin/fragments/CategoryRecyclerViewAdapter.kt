package com.roma.kotlin.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.roma.kotlin.R

import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.fragments.FragmentCategory.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_category.view.*

/**
 *
 */
class CategoryRecyclerViewAdapter(
        private var mValue: List<Category>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

//    private val categoryList: List<Category>? = null
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Category
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValue[position]
        holder.mIdView.text = item.uid.toString()
        holder.mContentView.text = item.name

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValue.size

    fun updateData(categoryList: List<Category>) {
        mValue = categoryList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
