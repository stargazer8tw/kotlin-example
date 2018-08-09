package com.roma.kotlin.fragments

import android.support.v4.app.DialogFragment

/**
 * implements this when fragment need activity to re-enable drawer
 */
interface OnDialogInteractionListener {
    fun onOpenDialogInteraction(dialog : DialogFragment)
    fun onCloseDialogInteraction()
}