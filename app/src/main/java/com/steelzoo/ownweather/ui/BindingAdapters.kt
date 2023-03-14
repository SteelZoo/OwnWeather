package com.steelzoo.ownweather.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("drawableSrc")
fun drawableSrc(imageView: ImageView, drawableAddress: Int){
    imageView.setImageResource(drawableAddress)
}