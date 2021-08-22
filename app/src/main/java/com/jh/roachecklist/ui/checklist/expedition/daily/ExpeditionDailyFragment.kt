package com.jh.roachecklist.ui.checklist.expedition.daily

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityExpeditionDailyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpeditionDailyFragment: BaseFragment<ActivityExpeditionDailyBinding, ExpeditionDailyViewModel>() {

    override val viewModel: ExpeditionDailyViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_expedition_daily

    private val expeditionDailyAdapter: ExpeditionDailyAdapter by lazy { ExpeditionDailyAdapter( onChecked,onClickNoti ) }

    override fun initViewsAndEvents() {

        viewModel.setCharacterInfo()

        dataBinding.rvExpeditionDaily.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = expeditionDailyAdapter

        }

        viewModel.expeditionDaily.observe( viewLifecycleOwner, {

            expeditionDailyAdapter.submitList( it )

        })

    }

    private val onChecked = { view: View, pos: Int ->

        if ( ( view as CheckBox).isChecked ) {

            viewModel.increaseCheckedCount(pos)
            expeditionDailyAdapter.notifyItemChanged( pos )

        } else {

            viewModel.decreaseCheckedCount( pos )
            expeditionDailyAdapter.notifyItemChanged( pos )

        }

    }

    private val onClickNoti = { position: Int ->

        viewModel.onClickNoti( position )
        expeditionDailyAdapter.notifyItemChanged( position )

    }

}