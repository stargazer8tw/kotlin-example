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
class CategoryRecyclerViewAdapter : ListAdapter<Category, CategoryRecyclerViewAdapter.ViewHolder>(CategoryDiffCallback()) {

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
}
