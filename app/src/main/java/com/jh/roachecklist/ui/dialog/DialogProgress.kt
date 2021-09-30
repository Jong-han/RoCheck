package com.jh.roachecklist.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.jh.roachecklist.R

class DialogProgress constructor(context: Context): Dialog( context ){

    init {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView( R.layout.dialog_progress )
        setCancelable( false )
        setCanceledOnTouchOutside(false)

    }

}