package com.roma.kotlin.fragments

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.roma.kotlin.R

import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.fragments.FragmentCategory.OnListFragmentInteractionListener
import com.roma.kotlin.adapters.CategoryDiffCallback
import kotlinx.android.synthetic.main.fragment_category.view.*

/**
 * @see https://github.com/googlesamples/android-sunflower/blob/master/app/src/main/java/com/google/samples/apps/sunflower/adapters/PlantAdapter.kt
 */
//class CategoryRecyclerViewAdapter(
//        private var mValue: List<Category>,
//        private val mListener: OnListFragmentInteractionListener?)
//    : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

class CategoryRecyclerViewAdapter : ListAdapter<Category, CategoryRecyclerViewAdapter.ViewHolder>(CategoryDiffCallback()) {

//    private val categoryList: List<Category>? = null
//    private val mOnClickListener: View.OnClickListener
//
//    init {
//        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as Category
//            // Notify the active callbacks interface (the activity, if the fragment is attached to
//            // one) that an item has been selected.
//            mListener?.onListFragmentInteraction(item)
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category, parent, false)
        return ViewHolder(view)
//        return ViewHolder(ListItemPlantBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = mValue[position]
//        holder.mIdView.text = item.uid.toString()
//        holder.mContentView.text = item.name
//
//        with(holder.mView) {
//            tag = item
//            setOnClickListener(mOnClickListener)
//        }
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

//    override fun getItemCount(): Int = mValue.size

//    fun updateData(categoryList: List<Category>) {
//        mValue = categoryList
//        notifyDataSetChanged()
//    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
//        val mIdView: TextView = mView.item_number
//        val mContentView: TextView = mView.content
//
//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
        fun bind(listener: View.OnClickListener, item: Category) {
            mView.item_number.text = "$item.uid"
            mView.content.text = item.name
            mView.setOnClickListener { listener }
//            clickListener = listener
//            plant = item
//            executePendingBindings()
        }
    }
}
