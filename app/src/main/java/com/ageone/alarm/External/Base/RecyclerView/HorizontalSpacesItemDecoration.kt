package com.ageone.alarm.External.Base.RecyclerView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class HorizontalSpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val isLast = position == state.itemCount - 1
        if (isLast) {
            outRect.right = space
            outRect.left = 0
        }
        if (position == 0) {
            outRect.left = space
            if (!isLast)
                outRect.right = 0
        }
    }
}