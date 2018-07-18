package com.roma.kotlin.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater
import android.widget.EditText
import android.widget.Toast
// use kotlin extension
import kotlinx.android.synthetic.main.fragment_add_category.editCategoryName
import com.roma.kotlin.R
import com.roma.kotlin.db.obj.Category
import com.roma.kotlin.model.CategoryListViewModel
import com.roma.kotlin.ext.nonNullObserve

class FragmentAddCategory : DialogFragment() {

    private var listener: OnCloseDialogInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_category, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_button_save -> {
                // TODO save category
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
        var viewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        var duplicated = false
        viewModel.getAllListCategory().nonNullObserve(this, {
            duplicated = it.any { category -> category.name.equals(txt) }
        })
        if (duplicated) {
            editCategoryName.error = "Category Name is duplicated"
            editCategoryName.requestFocus()
            return
        } else {
            viewModel.addCategory(Category(0, txt, 0))
            Toast.makeText(activity, "saved", Toast.LENGTH_SHORT).show()
        }
        listener?.onCloseDialogInteraction()
        dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCloseDialogInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCloseDialogInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {
        const val TAG = "AddCategoryDialog"
    }
}