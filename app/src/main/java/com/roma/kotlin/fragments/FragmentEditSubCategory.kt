package com.roma.kotlin.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater
import android.widget.TextView
import android.widget.Toast
// use kotlin extension
import kotlinx.android.synthetic.main.fragment_add_category.editCategoryName
import com.roma.kotlin.R
import com.roma.kotlin.model.CategoryListViewModel
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.db.obj.SubCategory
import com.roma.kotlin.utils.InjectorUtils
import com.roma.kotlin.ext.nonNullObserve
import java.util.UUID

class FragmentEditSubCategory(val category : Category, val subcategory: SubCategory) : DialogFragment() {

    private var listener: OnDialogInteractionListener? = null
    private lateinit var viewModel: CategoryListViewModel

    constructor (category : Category) : this(category, SubCategory(UUID.randomUUID().toString(), category.uid, "", Long.MAX_VALUE)) {
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_category, container, false)
        val context = context ?: return view
        val factory = InjectorUtils.provideCategoryListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(CategoryListViewModel::class.java)
        view.findViewById<TextView>(R.id.editCategoryName).setText(subcategory.name)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_button_save -> {
                // TODO save subcategory
                onSavePressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onSavePressed() {
        var txt = editCategoryName.text.toString()
        if (txt.isNullOrBlank()) {
            editCategoryName.error = "Category Name is empty"
            editCategoryName.requestFocus()
            return
        }

        viewModel.getSubCategories(category).nonNullObserve(this, {
            var duplicated = false
            for (item in it) {
                if (subcategory.name.equals(txt)) {
                    // name is not change
                    continue
                } else if (item.name.equals(txt)) {
                    editCategoryName.error = "Category Name is duplicated"
                    editCategoryName.requestFocus()
                    duplicated = true
                    break
                }
            }
            if (!duplicated) {
                subcategory.name = txt
                viewModel.addSubCategory(subcategory)
                Toast.makeText(activity, "saved", Toast.LENGTH_SHORT).show()
                listener?.onCloseDialogInteraction()
                dismiss()
            }
        })
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
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {
        const val TAG = "EditCategoryDialog"
    }
}