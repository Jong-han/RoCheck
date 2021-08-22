package com.jh.roachecklist.ui.checklist.expedition.weekly

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityExpeditionWeeklyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpeditionWeeklyFragment: BaseFragment<ActivityExpeditionWeeklyBinding, ExpeditionWeeklyViewModel>() {

    override val viewModel: ExpeditionWeeklyViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_expedition_weekly

    private val expeditionWeeklyAdapter: ExpeditionWeeklyAdapter by lazy { ExpeditionWeeklyAdapter( onChecked, onClickNoti ) }

    override fun initViewsAndEvents() {

        viewModel.setCharacterInfo()

        dataBinding.rvExpeditionWeekly.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = expeditionWeeklyAdapter

        }

        viewModel.expeditionWeekly.observe( viewLifecycleOwner, {

            expeditionWeeklyAdapter.submitList( it )

        })

    }

    private val onChecked = { view: View, pos: Int ->

        if ( ( view as CheckBox).isChecked ) {

            viewModel.increaseCheckedCount(pos)
            expeditionWeeklyAdapter.notifyItemChanged( pos )

        } else {

            viewModel.decreaseCheckedCount( pos )
            expeditionWeeklyAdapter.notifyItemChanged( pos )

        }

    }

    private val onClickNoti = { position: Int ->

        viewModel.onClickNoti( position )
        expeditionWeeklyAdapter.notifyItemChanged( position )

    }


}