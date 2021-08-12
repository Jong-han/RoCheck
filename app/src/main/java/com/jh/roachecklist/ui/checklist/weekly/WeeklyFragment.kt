package com.jh.roachecklist.ui.checklist.weekly

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListWeeklyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.checklist.CheckListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeeklyFragment: BaseFragment<ActivityCheckListWeeklyBinding, WeeklyViewModel>() {
    override val viewModel: WeeklyViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_weekly

    private val weeklyAdapter: WeeklyAdapter by lazy { WeeklyAdapter( onChecked, onClickNoti ) }

    override fun initViewsAndEvents() {

        val nickName = arguments?.getString( CheckListActivity.EXTRA_BUNDLE_NICKNAME ) ?: ""
        val level = arguments?.getInt( CheckListActivity.EXTRA_BUNDLE_LEVEL ) ?: 0
        viewModel.setCharacterInfo( nickName, level )

        dataBinding.rvWeekly.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = weeklyAdapter

        }

        viewModel.weekly.observe( viewLifecycleOwner, {

            weeklyAdapter.submitList( it )

        })


    }

    private val onChecked: Function2<View,Int,Unit> = { view: View, pos: Int ->

        val item = weeklyAdapter.currentList[pos]

        val otherDifficulty = when {

            item.work.contains("노멀") -> pos + 1
            item.work.contains("하드") -> pos - 1
            else -> null

        }

        if ( ( view as CheckBox ).isChecked ) {

            if ( otherDifficulty != null ) {

                viewModel.increaseCheckedCount( pos, otherDifficulty  )
                weeklyAdapter.notifyItemChanged( pos )
                weeklyAdapter.notifyItemChanged( otherDifficulty )

            } else {

                viewModel.increaseCheckedCount( pos, null )
                weeklyAdapter.notifyItemChanged( pos )

            }

        }
        else {

            viewModel.decreaseCheckedCount( pos )
            weeklyAdapter.notifyItemChanged( pos )

        }

    }
    private val onClickNoti = { position: Int ->

        viewModel.onClickNoti( position )
        weeklyAdapter.notifyItemChanged( position )

    }

}