package com.jh.roachecklist.ui.guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityGuideBinding
import com.jh.roachecklist.ui.base.BaseActivity
import com.jh.roachecklist.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideActivity : BaseActivity<ActivityGuideBinding, GuideViewModel>() {

    override val viewModel: GuideViewModel by viewModels()

    override val bindingVariable: Int? = null

    override fun getLayoutId(): Int = R.layout.activity_guide

    override fun initViewAndEvent() {

    }
}