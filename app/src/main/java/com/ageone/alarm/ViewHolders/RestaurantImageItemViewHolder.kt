package com.ageone.alarm.UIComponents.ViewHolders

import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.External.Base.ImageView.BaseImageView
import com.ageone.alarm.External.Base.RecyclerView.BaseViewHolder
import com.ageone.alarm.External.Libraries.Glide.addImageFromGlide
import yummypets.com.stevia.*

class RestaurantImageItemViewHolder(val constraintLayout: ConstraintLayout) :
    BaseViewHolder(constraintLayout) {

    val imageViewFood by lazy {
        val image = BaseImageView()
        image.setBackgroundColor(Color.GRAY)
        image.initialize()
        image
    }

    init {

        renderUI()
    }

}

fun RestaurantImageItemViewHolder.renderUI() {
    constraintLayout.subviews(
        imageViewFood
    )

    imageViewFood
        .constrainTopToTopOf(constraintLayout)
        .fillHorizontally()
}

fun RestaurantImageItemViewHolder.initialize(width: Int, image: Int) {

    constraintLayout
        .width(width)

    imageViewFood
        .width(width)
        .height(width*.402F)

    addImageFromGlide(imageViewFood,image,1)
}
