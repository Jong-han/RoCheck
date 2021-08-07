package com.jh.roachecklist.utils

import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("android:visibleIf")
fun View.setVisibleIf( value: Boolean) {

    this.isVisible = value

}


@BindingAdapter("android:invisibleIf")
fun View.setInvisibleIf( value: Boolean) {

    this.isInvisible = value

}

