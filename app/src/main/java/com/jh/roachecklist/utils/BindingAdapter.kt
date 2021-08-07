package com.jh.roachecklist.utils

import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


object BindingAdapter {

    @BindingAdapter("android:visibleIf")
    @JvmStatic
    fun setVisibleIf( view: View, value: Boolean) {

        if ( value )
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
//        Log.i("zxcv"," visible :: ${this.isVisible}" )

    }


//    @BindingAdapter("android:invisibleIf")
//    @JvmStatic
//    fun View.setInvisibleIf(value: Boolean) {
//
//        if ( value )
//            view.visibility = View.VISIBLE
//        else
//            view.visibility = View.GONE
//    }

}

//@BindingAdapter("android:visibleIf")
//fun View.setVisibleIf(value: Boolean) {
//
//    this.isVisible = value
//    Log.i("zxcv"," visible :: ${this.isVisible}" )
//
//}
//
//
//@BindingAdapter("android:invisibleIf")
//fun View.setInvisibleIf(value: Boolean) {
//
//    this.isInvisible = value
//
//}