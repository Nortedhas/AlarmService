package com.ageone.alarm.External.Base.RecyclerView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ColumnEqualsPaddingItemDecoration(private val space: Int, private val columnNumber: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        if (columnNumber > 1) {
            if (position % columnNumber == 0) { //first element in row
                outRect.left = space
            }

            if (position % (columnNumber - 1) == 0) { //last element in row
                outRect.right = space
            }
        }
    }
}