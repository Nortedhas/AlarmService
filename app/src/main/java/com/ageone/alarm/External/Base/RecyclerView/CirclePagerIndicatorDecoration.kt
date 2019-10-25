package com.ageone.alarm.External.Base.RecyclerView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yummypets.com.stevia.dp
import kotlin.math.max


class CirclePagerIndicatorDecoration : RecyclerView.ItemDecoration() {
    private val colorActive = Color.parseColor("#f27727")
    private val colorInactive = Color.parseColor("#DEDEDE")

    private val mIndicatorHeight: Int = 56.dp

    private val mIndicatorStrokeWidth: Float = 1F.dp

    private val mIndicatorItemLength: Float = 17F.dp

    private val mIndicatorItemPadding: Float = 32F.dp

    private val mInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    private val mPaintActiveBorder = Paint()

    init {

        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.color = colorInactive
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.isAntiAlias = true

        mPaintActiveBorder.strokeWidth = mIndicatorStrokeWidth
        mPaintActiveBorder.color = colorInactive
        mPaintActiveBorder.style = Paint.Style.STROKE
        mPaintActiveBorder.isAntiAlias = true

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        parent.adapter?.let { adapter ->
            val itemCount = adapter.itemCount

            // center horizontally, calculate width and subtract half from center
            val totalLength = mIndicatorItemLength * itemCount
            val paddingBetweenItems = max(0, itemCount - 1) * mIndicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems

            val indicatorStartX = (parent.width - indicatorTotalWidth) / 2
            // center vertically in the allotted space
            val indicatorPosY = parent.height - mIndicatorHeight.toFloat()/2

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

            // find active pages (which should be highlighted)
            parent.layoutManager?.let { layoutManager ->
                if (layoutManager is LinearLayoutManager) {
                    val activePosition = layoutManager.findFirstVisibleItemPosition()
                    if (activePosition == RecyclerView.NO_POSITION) {
                        return
                    }

                    // find offset of active pages (if the user is scrolling)
                    layoutManager.findViewByPosition(activePosition)?.let { activeChild ->
                        val left = activeChild.left
                        val width = activeChild.width
                        val right = activeChild.right

                        // on swipe the active item will be positioned from [-width, 0]
                        // interpolate offset for smooth animation
                        val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())

                        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress)

//                        val progress = mInterpolator.getInterpolation(right / width.toFloat())

                    }
                }
            }

        }


    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
        mPaint.color = colorInactive

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2F, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float
    ) {
        mPaint.color = colorActive

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

//        Timber.i("$progress --")

        if (progress == 0F) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition

            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2F, mPaint)
            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2F, mPaintActiveBorder)

        } else {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress

            c.drawCircle(highlightStart + partialLength, indicatorPosY, mIndicatorItemLength / 2F, mPaint)
            c.drawCircle(highlightStart + partialLength, indicatorPosY, mIndicatorItemLength / 2F, mPaintActiveBorder)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }
}