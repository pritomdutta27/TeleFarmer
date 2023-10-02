package com.theroyalsoft.telefarmer.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.R
import kotlin.math.ceil

/**
 * Created by Pritom Dutta on 20/5/23.
 */
class RecyclerViewItemDecoration(
    internal val spacingPx: Int,
    internal val context: Context? = null
) : RecyclerView.ItemDecoration() {

    /**
     * Retrieve any offsets for the given item.
     *
     * @param outRect Rect to receive the output.
     * @param view The child view to decorate
     * @param parent RecyclerView this ItemDecoration is decorating
     * @param state The current state of RecyclerView.
     * @see RecyclerView.ItemDecoration.getItemOffsets
     */
    private var mDivider: Drawable? = null

    init {
        context?.let {
            mDivider = ContextCompat.getDrawable(context, R.drawable.bottom_stroke)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> configSpacingForGridLayoutManager(
                outRect = outRect,
                layoutManager = layoutManager,
                position = parent.getChildViewHolder(view).adapterPosition,
                itemCount = state.itemCount
            )
            is LinearLayoutManager -> configSpacingForLinearLayoutManager(
                outRect = outRect,
                layoutManager = layoutManager,
                position = parent.getChildViewHolder(view).adapterPosition,
                itemCount = state.itemCount
            )
        }
    }

    // ============================================================================================
    //  Private configs methods
    // ============================================================================================

    /**
     * Configure spacing for grid layout, given a rectangle.
     *
     * @param outRect Rect to modify.
     * @param layoutManager The currently responsible for layout policy.
     * @param position Position of the item represented by this ViewHolder.
     * @param itemCount The total number of items that can be laid out.
     */
    private fun configSpacingForGridLayoutManager(
        outRect: Rect,
        layoutManager: GridLayoutManager,
        position: Int,
        itemCount: Int
    ) {
        val cols = layoutManager.spanCount
        val rows = ceil(itemCount / cols.toDouble()).toInt()

        outRect.top = spacingPx
        outRect.left = 0
        outRect.right = 0
        outRect.bottom = if (position / cols == rows - 1) spacingPx else 0
    }

    /**
     * Configure spacing for linear layout, given a rectangle.
     *
     * @param outRect Rect to modify.
     * @param layoutManager The currently responsible for layout policy.
     * @param position Position of the item represented by this ViewHolder.
     * @param itemCount The total number of items that can be laid out.
     */
    private fun configSpacingForLinearLayoutManager(
        outRect: Rect,
        layoutManager: LinearLayoutManager,
        position: Int,
        itemCount: Int
    ) {
        outRect.top = spacingPx
//        outRect.left = spacingPx
        if (layoutManager.canScrollHorizontally()) {
//            outRect.right = if (position == itemCount - 1) spacingPx else 0
            outRect.bottom = spacingPx
        } else if (layoutManager.canScrollVertically()) {
//            outRect.right = spacingPx
            outRect.bottom = if (position == itemCount - 1) spacingPx else 0
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = spacingPx
        val right = parent.width - spacingPx
        val childCount = parent.childCount
        mDivider?.let { divider ->
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }
}
