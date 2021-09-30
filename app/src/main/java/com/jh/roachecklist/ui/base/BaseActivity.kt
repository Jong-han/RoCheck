package com.jh.roachecklist.ui.base

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jh.roachecklist.ui.dialog.DialogProgress

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

    private val progress by lazy { DialogProgress( this ) }

    fun showProgress() {
        if ( !progress.isShowing ) progress.show()
    }
    fun dismissProgress() {
        progress.dismiss()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        with( window ) {

            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            // set an slide transition
            val endTransition = Slide(Gravity.END).apply {
                duration = 300
                excludeTarget( android.R.id.statusBarBackground, true )

            }
            val startTransition = Slide(Gravity.START).apply {

                duration = 300

                excludeTarget( android.R.id.statusBarBackground, true )

            }
            enterTransition = endTransition
            exitTransition = startTransition

        }

        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<T>( this, getLayoutId() ).apply {

            dataBinding = this
            lifecycleOwner = this@BaseActivity
            bindingVariable?.let { setVariable( it, viewModel ) }

        }
        initViewAndEvent()

    }

}