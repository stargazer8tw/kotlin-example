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

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [FragmentCategory.OnListFragmentInteractionListener] interface.
 */
class FragmentCategory : Fragment() {

    private var columnCount = 1
//    private lateinit var adapter: CategoryRecyclerViewAdapter
    private lateinit var viewModel: CategoryListViewModel
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
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

        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//            }
//        }
        val adapter = CategoryRecyclerViewAdapter()
        view.findViewById<RecyclerView>(R.id.category_list).adapter = adapter
        subscribeUi(adapter)
        // https://stackoverflow.com/questions/44489235/update-recyclerview-with-android-livedata
//        adapter?.let { adapter ->
//            var viewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
//            viewModel.getCategories().nonNullObserve(this, {
//                adapter.updateData(it)
//            })
//        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun subscribeUi(adapter: CategoryRecyclerViewAdapter) {
//        viewModel.getCategories().observe(viewLifecycleOwner, Observer { categories ->
//            if (categories != null) adapter.submitList(categories)
//        })
        viewModel.getCategories().nonNullObserve(this, { categories ->
            if (categories != null) adapter.submitList(categories)
        })
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(category: Category?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                FragmentCategory().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
