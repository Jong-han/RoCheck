package com.jh.roachecklist.ui.checklist.daily

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListDailyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.character.CharacterViewModel
import com.jh.roachecklist.ui.checklist.CheckListModel
import com.jh.roachecklist.ui.checklist.CheckListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFragment: BaseFragment<ActivityCheckListDailyBinding, CheckListViewModel>() {

    override val viewModel: CheckListViewModel by activityViewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_daily

    private val dailyAdapter: DailyAdapter by lazy { DailyAdapter( ) }

    override fun initViewsAndEvents() {

        dataBinding.rvDaily.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = dailyAdapter

        }

        dailyAdapter.submitList( viewModel.daily )


    }

}