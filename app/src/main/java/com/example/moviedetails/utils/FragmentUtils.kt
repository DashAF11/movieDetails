package com.example.moviedetails.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun Fragment.navigate(@IdRes resId: Int) =
    try {
        findNavController().navigate(resId)
    } catch (e: IllegalArgumentException) {
        Timber.e(e)
    }

fun Fragment.navigate(directions: NavDirections) =
    try {
        findNavController().navigate(directions)
    } catch (e: Exception) {
        Timber.e(e)
    }

