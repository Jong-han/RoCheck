package com.jh.roachecklist.ui.checklist.expedition

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityExpeditonBinding
import com.jh.roachecklist.ui.base.BaseActivity
import com.jh.roachecklist.ui.character.CharacterActivity
import com.jh.roachecklist.ui.checklist.CheckListActivity
import com.jh.roachecklist.ui.checklist.expedition.daily.ExpeditionDailyFragment
import com.jh.roachecklist.ui.checklist.expedition.weekly.ExpeditionWeeklyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpeditionActivity: BaseActivity<ActivityExpeditonBinding, ExpeditionViewModel>() {

    companion object {

        const val DAILY = 0
        const val WEEKLY = 1

        const val EXTRA_BUNDLE_EXPEDITION_LEVEL = "EXTRA_BUNDLE_EXPEDITION_LEVEL"

    }

    override val viewModel: ExpeditionViewModel by viewModels()
    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_expediton

    private val expeditionDailyFragment = ExpeditionDailyFragment()
    private val expeditionWeeklyFragment = ExpeditionWeeklyFragment()
    private val fragmentList = arrayListOf( expeditionDailyFragment, expeditionWeeklyFragment )

    private var activeFragment: Fragment = expeditionDailyFragment

    override fun initViewAndEvent() {

        viewModel.setCharacterInfo()

        val bundle = setBundle()

        expeditionDailyFragment.arguments = bundle
        expeditionWeeklyFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add( R.id.container_expedition, expeditionDailyFragment )
            .add( R.id.container_expedition, expeditionWeeklyFragment )
            .hide( expeditionDailyFragment )
            .hide( expeditionWeeklyFragment )
            .show( activeFragment )
            .commit()


        addOrShowFragment( DAILY )

        dataBinding.tabs.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when ( tab?.position ) {

                    DAILY -> addOrShowFragment(DAILY)
                    WEEKLY -> addOrShowFragment(WEEKLY)

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

    }

    private fun addOrShowFragment( index: Int ) {

        val fragment= fragmentList[index]

        val transaction = supportFragmentManager.beginTransaction()

        transaction.hide( activeFragment ).show( fragment )

        transaction.commit()

        activeFragment = fragment


    }

    private fun setBundle(): Bundle {

        val level = viewModel.level

        val bundle = Bundle()
        bundle.putInt( EXTRA_BUNDLE_EXPEDITION_LEVEL, level )

        return bundle

    }

}