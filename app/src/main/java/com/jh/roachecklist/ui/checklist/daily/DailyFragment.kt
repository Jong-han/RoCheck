package com.jh.roachecklist.ui.checklist.daily

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListDailyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.character.CharacterViewModel
import com.jh.roachecklist.ui.checklist.CheckListActivity
import com.jh.roachecklist.ui.checklist.CheckListModel
import com.jh.roachecklist.ui.checklist.CheckListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFragment: BaseFragment<ActivityCheckListDailyBinding, DailyViewModel>() {

    override val viewModel: DailyViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_daily

    private val dailyAdapter: DailyAdapter by lazy { DailyAdapter( onChecked ) }

    override fun initViewsAndEvents() {

        val nickName = arguments?.getString( CheckListActivity.EXTRA_BUNDLE_NICKNAME ) ?: ""
        val level = arguments?.getInt( CheckListActivity.EXTRA_BUNDLE_LEVEL ) ?: 0
        viewModel.setCharacterInfo( nickName, level )

        dataBinding.rvDaily.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = dailyAdapter

        }
        dailyAdapter.submitList( viewModel.daily )

        dailyAdapter.notifyDataSetChanged()


    }

    private val onChecked = { view: View, pos: Int ->

        val item = dailyAdapter.currentList[pos]
        if ( ( view as CheckBox).isChecked )
            viewModel.increaseCheckedCount( item.work )
        else
            viewModel.decreaseCheckedCount( item.work )

    }

}