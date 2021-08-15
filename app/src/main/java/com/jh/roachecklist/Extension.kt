package com.jh.roachecklist

import android.widget.CheckBox

fun CheckBox.setCheckBox( count: Int, index: Int ) {

    isChecked = count >= index

}