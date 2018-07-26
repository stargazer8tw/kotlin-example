package com.roma.kotlin.fragments.helper

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * @see https://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper
 * @see https://rayzhangweb.wordpress.com/2017/07/08/android-itemtouchhelper%E8%AE%93recycleview-item%E5%8B%95%E8%B5%B7%E4%BE%86%E5%90%A7/
 * @see https://therubberduckdev.wordpress.com/2017/10/24/android-recyclerview-drag-and-drop-and-swipe-to-dismiss/
 * @see https://github.com/sjthn/RecyclerViewDemo/tree/advanced-usecases
 */
class SwipeAndDragHelper(private val contract: ItemMoveSwipeListener) : ItemTouchHelper.Callback() {

//    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white)

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        contract.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        contract.onItemSwiped(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                val alpha = 1 - Math.abs(dX) / recyclerView.width
                viewHolder.itemView.alpha = alpha
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    interface ItemMoveSwipeListener {

        fun onItemMoved(oldPosition: Int, newPosition: Int)

        fun onItemSwiped(position: Int)
    }
}