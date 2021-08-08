package com.jh.roachecklist.ui.checklist.raid

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListRaidBinding
import com.jh.roachecklist.ui.base.BaseFragment
import com.jh.roachecklist.ui.checklist.CheckListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaidFragment: BaseFragment<ActivityCheckListRaidBinding, RaidViewModel>() {

    override val viewModel: RaidViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list_raid

    private val raidAdapter: RaidAdapter by lazy { RaidAdapter( onChecked ) }

    override fun initViewsAndEvents() {


        val nickName = arguments?.getString( CheckListActivity.EXTRA_BUNDLE_NICKNAME ) ?: ""
        val level = arguments?.getInt( CheckListActivity.EXTRA_BUNDLE_LEVEL ) ?: 0
        viewModel.setCharacterInfo( nickName, level )

        dataBinding.rvRaid.apply {

            layoutManager = LinearLayoutManager( requireContext() )
            adapter = raidAdapter

        }

        viewModel.raid.observe( viewLifecycleOwner, {

            raidAdapter.submitList( it )

        })

    }

    private val onChecked: Function2<View,Int,Unit> = { view: View, pos: Int ->

        val item = raidAdapter.currentList[pos]

        val otherDifficulty = when {

            item.work.contains("노멀") -> pos + 1
            item.work.contains("하드") -> pos - 1
            else -> null

        }

        if ( ( view as CheckBox ).isChecked ) {

            if ( otherDifficulty != null ) {

                viewModel.increaseCheckedCount( pos, otherDifficulty  )
                raidAdapter.notifyItemChanged( pos )
                raidAdapter.notifyItemChanged( otherDifficulty )

            } else {

                viewModel.increaseCheckedCount( pos, null )
                raidAdapter.notifyItemChanged( pos )

            }

        }
        else {

            viewModel.decreaseCheckedCount( pos )
            raidAdapter.notifyItemChanged( pos )

        }
//        raidAdapter.notifyDataSetChanged()

//        raidAdapter.notif

    }

}