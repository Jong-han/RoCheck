package com.jh.roachecklist.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun AppCompatActivity.setSupportActionBar(toolbar: Toolbar, isHomeAsUp:Boolean ) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(isHomeAsUp)
    supportActionBar?.setDisplayShowTitleEnabled(false)
}

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel>: AppCompatActivity() {

    lateinit var dataBinding: T
    abstract val viewModel: V
    abstract val bindingVariable: Int?
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun initViewAndEvent()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<T>( this, getLayoutId() ).apply {

            dataBinding = this
            lifecycleOwner = this@BaseActivity
            bindingVariable?.let { setVariable( it, viewModel ) }

        }
        initViewAndEvent()

    }

}