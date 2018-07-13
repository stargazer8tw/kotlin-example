package com.roma.kotlin.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.roma.kotlin.R

class DialogAddCategory : DialogFragment() {

    val TAG = "FullScreenDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
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
        return inflater.inflate(R.layout.dialog_layout, container, false)
    }

    companion object {
        const val TAG = "FullScreenDialog"
    }

}
