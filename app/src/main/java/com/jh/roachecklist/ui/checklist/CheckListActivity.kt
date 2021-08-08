package com.jh.roachecklist.ui.checklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListBinding
import com.jh.roachecklist.ui.base.BaseActivity
import com.jh.roachecklist.ui.base.BaseViewModel
import com.jh.roachecklist.ui.character.CharacterActivity
import com.jh.roachecklist.ui.checklist.daily.DailyFragment
import com.jh.roachecklist.ui.checklist.raid.RaidFragment
import com.jh.roachecklist.ui.checklist.weekly.WeeklyFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckListActivity : BaseActivity<ActivityCheckListBinding, CheckListViewModel>() {

    companion object {

        const val DAILY = 0
        const val WEEKLY = 1
        const val RAID = 2

        const val EXTRA_BUNDLE_NICKNAME = "EXTRA_BUNDLE_NICKNAME"
        const val EXTRA_BUNDLE_LEVEL = "EXTRA_BUNDLE_LEVEL"

    }

    override val viewModel: CheckListViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list

    private val dailyFragment: Fragment = DailyFragment()
    private val weeklyFragment: Fragment = WeeklyFragment()
    private val raidFragment: Fragment = RaidFragment()

    private var activeFragment: Fragment = dailyFragment

    private val fragmentList = arrayListOf( dailyFragment, weeklyFragment, raidFragment )

    override fun initViewAndEvent() {

        val bundle = setBundle()

        dailyFragment.arguments = bundle
        weeklyFragment.arguments = bundle
        raidFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add( R.id.fragment_container, dailyFragment )
            .add( R.id.fragment_container, weeklyFragment )
            .add( R.id.fragment_container, raidFragment )
            .hide( weeklyFragment )
            .hide( raidFragment )
            .show( activeFragment )
            .commit()


        addOrShowFragment( DAILY )

        dataBinding.tabs.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when ( tab?.position ) {

                    DAILY -> addOrShowFragment( DAILY )
                    WEEKLY -> addOrShowFragment( WEEKLY )
                    else -> addOrShowFragment( RAID )

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

    }

    private fun addOrShowFragment( index: Int ) {

        val fragment=  fragmentList[index]

        val transaction = supportFragmentManager.beginTransaction()

        transaction.hide( activeFragment  ).show( fragment )

        transaction.commit()

        activeFragment = fragment

    }

    private fun setBundle(): Bundle {

        val nickName = intent.getStringExtra( CharacterActivity.EXTRA_NICK_NAME )
        val level = intent.getIntExtra( CharacterActivity.EXTRA_LEVEL, 0 )

        val bundle = Bundle()
        bundle.putInt( EXTRA_BUNDLE_LEVEL, level )
        bundle.putString( EXTRA_BUNDLE_NICKNAME, nickName)

        return bundle

    }

}