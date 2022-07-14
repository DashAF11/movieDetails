package com.example.moviedetails.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun String.loadImage(imageView: ImageView) {
    Glide.with(imageView).load("https://image.tmdb.org/t/p/w200$this").into(imageView)
}