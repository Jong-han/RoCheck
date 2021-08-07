package com.jh.roachecklist.ui.checklist.raid

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListRaidBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.checklist.CheckListViewModel
import com.jh.roachecklist.ui.checklist.daily.DailyAdapter

class RaidFragment: BaseFragment<ActivityCheckListRaidBinding, CheckListViewModel>() {
    override val viewModel: CheckListViewModel by activityViewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_raid

    private val raidAdapter: RaidAdapter by lazy { RaidAdapter( ) }

    override fun initViewsAndEvents() {

        dataBinding.rvRaid.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = raidAdapter

        }

        raidAdapter.submitList( viewModel.raid )


    }
}