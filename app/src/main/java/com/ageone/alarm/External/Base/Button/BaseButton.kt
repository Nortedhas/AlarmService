package com.ageone.alarm.External.Base.Button

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import com.ageone.alarm.Application.currentActivity
import yummypets.com.stevia.dp
import android.graphics.drawable.VectorDrawable
import com.ageone.alarm.R

class BaseButton: Button(currentActivity) {
    val gradientDrawable = GradientDrawable()

    var gradient: Int? = null
    var orientation: GradientDrawable.Orientation? = null

    var cornerRadius: Int? = null
    var backgroundColor: Int? = null

    var imageIcon: Int? = null

    var sizeIcon = Pair(22F, 22F)

    var borderColor: Int? = null
    var borderWidth: Int? = null

    fun initialize() {
        isAllCaps = false
        gravity = Gravity.CENTER
        stateListAnimator = null

        gradientDrawable.shape = GradientDrawable.RECTANGLE

        cornerRadius?.let { cornerRadius ->
            gradientDrawable.cornerRadius = cornerRadius.toFloat()
        }

        backgroundColor?.let { backgroundColor ->
            gradientDrawable.setColor(backgroundColor)
            gradient?.let { gradient ->
                gradientDrawable.colors = intArrayOf(backgroundColor, gradient)
            }
        }

        orientation?.let { orientation ->
            gradientDrawable.orientation = orientation
        }


        imageIcon?.let { imageIcon ->
            val drawable = currentActivity?.resources?.getDrawable(imageIcon)

            val bitmap = when (drawable){
                is BitmapDrawable -> {
                    drawable.bitmap
                }

                is VectorDrawable -> {
                    val bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bitmap

                }
                else -> {
                    currentActivity?.resources?.getDrawable(R.drawable.ic_arrow_back) as Bitmap//заглушка
                }
            }

            val d = BitmapDrawable(currentActivity?.resources,
                Bitmap.createScaledBitmap(bitmap, sizeIcon.first.dp.toInt(), sizeIcon.second.dp.toInt(), true))
            setCompoundDrawablesWithIntrinsicBounds(d, null, null, null)
            setPadding(50, 0, 10, 0)
        }

        borderWidth?.let { borderWidth ->
            borderColor?.let { borderColor ->
                gradientDrawable.setStroke(borderWidth, borderColor)
            }
        }

        background = gradientDrawable
    }
}