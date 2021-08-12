package com.jh.roachecklist.ui.checklist.expedition

import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityExpeditonBinding
import com.jh.roachecklist.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpeditionActivity: BaseActivity<ActivityExpeditonBinding, ExpeditionViewModel>() {
    override val viewModel: ExpeditionViewModel by viewModels()
    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_expediton

    private val expeditionAdapter: ExpeditionAdapter by lazy { ExpeditionAdapter( onChecked, onClickNoti ) }

    override fun initViewAndEvent() {

        viewModel.setCharacterInfo()

        dataBinding.rvExpedition.apply {

            adapter = expeditionAdapter
            layoutManager = LinearLayoutManager( this@ExpeditionActivity )

        }

        viewModel.expedition.observe( this, {

            expeditionAdapter.submitList( it )

        })

    }

    private val onChecked = { view: View, pos: Int ->

        if ( ( view as CheckBox).isChecked ) {

            viewModel.increaseCheckedCount(pos)
            expeditionAdapter.notifyItemChanged( pos )

        } else {

            viewModel.decreaseCheckedCount( pos )
            expeditionAdapter.notifyItemChanged( pos )

        }

    }

    private val onClickNoti = { position: Int ->

        viewModel.onClickNoti( position )
        expeditionAdapter.notifyItemChanged( position )

    }

}