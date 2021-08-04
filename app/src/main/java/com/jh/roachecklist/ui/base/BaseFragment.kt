package com.jh.roachecklist.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel>: Fragment() {

    lateinit var dataBinding: T

    abstract val viewModel: V?

    abstract val bindingVariable: Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViewsAndEvents()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataBinding = DataBindingUtil.inflate( layoutInflater, getLayoutId(), container, false )
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable( bindingVariable, viewModel )

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewsAndEvents()
    }

    // 메뉴 사용 Fragment 가 많을 경우 주석 해제
//    abstract fun getMenuId(): Int
//    abstract val hasOptionMenu: Boolean


    // 메뉴 사용 Fragment 가 많을 경우 주석 해제
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//
//        if ( hasOptionMenu && getMenuId() != -1 ) {
//
//            inflater.inflate( getMenuId(), menu )
//
//        }
//        super.onCreateOptionsMenu(menu, inflater)
//
//    }

}