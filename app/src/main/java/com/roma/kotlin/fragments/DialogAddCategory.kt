package com.roma.kotlin.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Menu
import android.view.MenuItem
import com.roma.kotlin.R
import android.widget.Toast
import android.text.method.TextKeyListener.clear
import android.view.MenuInflater
import kotlinx.android.synthetic.main.app_bar_main.*
import com.roma.kotlin.R.id.toolbar




class DialogAddCategory : DialogFragment() {

    val TAG = "FullScreenDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
//        setCancelable(true)
//        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
//        dialog?.let{
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog.window.setLayout(width, height)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.dialog_layout, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.dialogToolBar)
        toolbar.inflateMenu(R.menu.menu_save)
        toolbar.setNavigationIcon(R.drawable.ic_menu_close)
        toolbar.setNavigationOnClickListener {
            // we don't need keep state after close
            dismissAllowingStateLoss()
        }
        return view
//        return inflater.inflate(R.layout.dialog_layout, container, false)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        menu.clear()
//        inflater.inflate(R.menu.menu_save, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.getItemId()) {
//            R.menu.menu_save -> {
//                Toast.makeText(activity, "saved", Toast.LENGTH_SHORT).show()
//                dismissAllowingStateLoss()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    companion object {
        const val TAG = "FullScreenDialog"
    }

}
