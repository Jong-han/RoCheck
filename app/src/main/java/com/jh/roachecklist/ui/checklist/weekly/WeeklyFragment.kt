package com.jh.roachecklist.ui.checklist.weekly

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListWeeklyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.checklist.CheckListViewModel
import com.jh.roachecklist.ui.checklist.raid.RaidAdapter

class WeeklyFragment: BaseFragment<ActivityCheckListWeeklyBinding, CheckListViewModel>() {
    override val viewModel: CheckListViewModel by activityViewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_weekly

    private val weeklyAdapter: WeeklyAdapter by lazy { WeeklyAdapter( ) }

    override fun initViewsAndEvents() {

        dataBinding.rvWeekly.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = weeklyAdapter

        }

        weeklyAdapter.submitList( viewModel.weekly )


    }
}