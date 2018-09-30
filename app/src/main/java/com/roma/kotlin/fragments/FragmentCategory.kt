package com.roma.kotlin.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roma.kotlin.R

import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.model.CategoryListViewModel
import com.roma.kotlin.utils.InjectorUtils
import com.roma.kotlin.ext.nonNullObserve
import android.support.v7.widget.helper.ItemTouchHelper
import com.roma.kotlin.fragments.helper.SwipeAndDragHelper
import com.roma.kotlin.MainActivity


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [FragmentCategory.OnListFragmentInteractionListener] interface.
 */
class FragmentCategory : Fragment(), ListActionListener<Category> {

    private lateinit var viewModel: CategoryListViewModel
    private var listener: OnDialogInteractionListener? = null
    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    /**
     * https://google-developer-training.gitbooks.io/android-developer-advanced-course-practicals
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, container, false)
        val context = context ?: return view
        val factory = InjectorUtils.provideCategoryListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(CategoryListViewModel::class.java)
        val adapter = CategoryRecyclerViewAdapter(this)

        // swipe and drag implementation
        itemTouchHelper = ItemTouchHelper(SwipeAndDragHelper(adapter))
        itemTouchHelper?.let() {it.attachToRecyclerView(view.findViewById<RecyclerView>(R.id.category_list))}

        view.findViewById<RecyclerView>(R.id.category_list).adapter = adapter
        subscribeUi(adapter)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDialogInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnDialogInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun subscribeUi(adapter: CategoryRecyclerViewAdapter) {
        viewModel.getCategories().nonNullObserve(this, { categories ->
            if (categories != null) adapter.updateList(categories)
        })
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper?.let() {it.startDrag(viewHolder)}
    }

    override fun onDelete(category: Category) : Boolean {
        viewModel.deleteCategory(category)
        return true
    }

    override fun onDragComplete(categories: List<Category>) : Boolean {
        categories.forEach {
            viewModel.updateCategory(it)
        }
        return true
    }

    override fun onItemClick(action : Int, item : Category) : Boolean {
        when (action) {
            ACTION_ADD_SUBCATEGORY -> {
                listener?.let() { it.onOpenDialogInteraction(FragmentEditSubCategory(item), MainActivity.FAB_ACTION_EDIT_SUBCATEGORY) }
            }
            ACTION_LIST_SUBCATEGORY -> {
                listener?.let() { it.onOpenDialogInteraction(FragmentSubCategory(item), MainActivity.FAB_ACTION_EDIT_SUBCATEGORY) }
            }
            ACTION_EDIT_CATEGORY -> {
                listener?.let() { it.onOpenDialogInteraction(FragmentEditCategory(item), MainActivity.FAB_ACTION_EDIT_CATEGORY) }
            }
        }
        // call main activity

        return true
    }

    companion object {
        const val ACTION_ADD_SUBCATEGORY = 0
        const val ACTION_LIST_SUBCATEGORY = 1
        const val ACTION_EDIT_CATEGORY = 2
    }
}
