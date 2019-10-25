package com.ageone.alarm.External.Libraries.Glide

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ageone.alarm.Application.currentActivity
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import io.github.armcha.coloredshadow.ShadowImageView
import yummypets.com.stevia.dp


@GlideModule
class MyGlideModule : AppGlideModule()

fun addImageFromGlide(image: ImageView, uri: String, cornerRadius: Int = 8) {
    val placeholder = createDownloadPlaceholder()

    if (cornerRadius == 0) {
        GlideApp
            .with(image)
            .load(uri)
            .centerCrop()
            .placeholder(placeholder)
            .into(image)
    } else {
        GlideApp
            .with(image)
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(cornerRadius.dp))
            .placeholder(placeholder)
            .into(image)
    }


}

fun addImageFromGlide(image: ImageView, uri: Int, cornerRadius: Int = 8) {
    val placeholder = createDownloadPlaceholder()

    if (cornerRadius == 0) {
        GlideApp
            .with(image)
            .load(uri)
            .centerCrop()
            .placeholder(placeholder)
            .into(image)
    } else {
        GlideApp
            .with(image)
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(cornerRadius.dp))
            .placeholder(placeholder)
            .into(image)
    }

}

fun addImageFromGlideWithGradient(image: ImageView, uri: Int, colorFirst: Int, colorSecond: Int) {
    val placeholder = createDownloadPlaceholder()

    GlideApp
        .with(image)
        .load(uri)
        .fitCenter()
        .placeholder(placeholder)
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                val colors = intArrayOf(colorFirst, colorSecond)
                val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
                image.background = LayerDrawable(arrayOf(
                    resource,
                    gradientDrawable
                ))
            }
            override fun onLoadCleared(@Nullable placeholder: Drawable?) {}

        }
        )

}

fun addImageFromGlideWithShadow(image: ShadowImageView, uri: String, cornerRadius: Int = 8) {
    val placeholder = createDownloadPlaceholder()

    GlideApp
        .with(image)
        .load(uri)
        .transform(CenterCrop(), RoundedCorners(cornerRadius.dp))
        .placeholder(placeholder)
        .into(object : ViewTarget<ImageView, Drawable>(image) {
            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
                image.setImageDrawable(placeholder, withShadow = false)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                super.onLoadCleared(placeholder)
                image.setImageDrawable(placeholder, withShadow = false)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                image.setImageDrawable(errorDrawable, withShadow = false)
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                image.setImageDrawable(resource)
            }
        })

}

private fun createDownloadPlaceholder(): LayerDrawable {
    val circularProgressDrawable = CircularProgressDrawable(currentActivity as Context)
    circularProgressDrawable.strokeWidth = 10f.dp
    circularProgressDrawable.centerRadius = 40f.dp
    circularProgressDrawable.setColorSchemeColors(Color.WHITE)
    circularProgressDrawable.start()

    val placeholderImage = GradientDrawable()
    placeholderImage.setColor(Color.GRAY)

    val placeholder = LayerDrawable(
        arrayOf(
            placeholderImage,
            circularProgressDrawable
        )
    )
    return placeholder
}
