package com.jh.roachecklist.ui.checklist.daily

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListDailyBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.checklist.CheckListActivity
import com.jh.roachecklist.ui.dialog.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFragment: BaseFragment<ActivityCheckListDailyBinding, DailyViewModel>() {

    override val viewModel: DailyViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_daily

    private val dailyAdapter: DailyAdapter by lazy { DailyAdapter( onChecked, onClickNoti ) }

    override fun initViewsAndEvents() {

        val nickName = arguments?.getString( CheckListActivity.EXTRA_BUNDLE_NICKNAME ) ?: ""
        val level = arguments?.getInt( CheckListActivity.EXTRA_BUNDLE_LEVEL ) ?: 0
        viewModel.setCharacterInfo( nickName, level )

        dataBinding.rvDaily.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = dailyAdapter

        }
        viewModel.daily.observe( viewLifecycleOwner, {

            dailyAdapter.submitList( it )

        })
        viewModel.clickSettingRest.observe( viewLifecycleOwner, {

            DialogUtil.showSettingRestDialog( requireContext(), layoutInflater ) { efona: Int, guardian: Int, chaos: Int ->

                viewModel.editRestBonus( efona, guardian, chaos )
//                requireActivity().finish()

            }

        })

    }

    private val onChecked = { view: View, pos: Int ->

        if ( ( view as CheckBox).isChecked ) {

            viewModel.increaseCheckedCount(pos)
            dailyAdapter.notifyItemChanged( pos )

        } else {

            viewModel.decreaseCheckedCount( pos )
            dailyAdapter.notifyItemChanged( pos )

        }

    }

    private val onClickNoti = { position: Int ->

        viewModel.onClickNoti( position )
        dailyAdapter.notifyItemChanged( position )

    }

}