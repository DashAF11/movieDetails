package com.example.moviedetails.domain

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

fun <T>MutableSharedFlow<T>.toSharedFlow() : SharedFlow<T> = this