package com.jh.roachecklist.ui.qna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityQnaBinding
import com.jh.roachecklist.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QnaActivity : BaseActivity<ActivityQnaBinding, QnaViewModel>() {

    override val viewModel: QnaViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_qna

    override fun initViewAndEvent() {
    }

}