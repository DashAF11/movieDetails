package com.example.moviedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.moviedetails.databinding.CustomSnackbarLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import timber.log.Timber

fun showCustomSnackBar(view: View?, context: Context, message: String) {
    try {
        val snackbar = Snackbar.make(view!!, "", Snackbar.LENGTH_LONG)
        val layout = snackbar.view as SnackbarLayout
        val binding: CustomSnackbarLayoutBinding =
            CustomSnackbarLayoutBinding.inflate(LayoutInflater.from(context))

        binding.snackBarMessage.text = message

        layout.addView(binding.root, 0)
        binding.snackBarAction.setOnClickListener { snackbar.dismiss() }
        snackbar.show()
    } catch (e: Exception) {
        Timber.e(e)
    }
}
