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

    }

    override val viewModel: CheckListViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_check_list

    private val dailyFragment: Fragment? = null
    private val weeklyFragment: Fragment? = null
    private val raidFragment: Fragment? = null

    private var activeFragment: Fragment? = null

    private val fragmentList = arrayListOf( dailyFragment, weeklyFragment, raidFragment )

    override fun initViewAndEvent() {

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

        var fragment = fragmentList[index]

        val transaction = supportFragmentManager.beginTransaction()

        if ( fragment == null ) {

            fragment = when ( index ) {

                DAILY -> DailyFragment()
                WEEKLY -> WeeklyFragment()
                else -> RaidFragment()

            }
            transaction.add( R.id.fragment_container, fragment )
            fragmentList[index] = fragment

            if ( activeFragment != null ) {

                transaction.show( fragment ).hide( activeFragment ?: DailyFragment() )

            } else {

                transaction.show( fragment )

            }

        } else {

            if ( activeFragment != null ) {

                transaction.hide( activeFragment ?: DailyFragment() ).show( fragment )

            } else {

                transaction.show( fragment )

            }

        }
        transaction.commit()
        activeFragment = fragment

    }

}