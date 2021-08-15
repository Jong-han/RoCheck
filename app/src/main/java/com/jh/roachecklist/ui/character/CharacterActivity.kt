package com.jh.roachecklist.ui.character

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.jh.roachecklist.BR
import com.jh.roachecklist.Const
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterBinding
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.ui.base.BaseActivity
import com.jh.roachecklist.ui.base.setSupportActionBar
import com.jh.roachecklist.ui.checklist.CheckListActivity
import com.jh.roachecklist.ui.checklist.expedition.ExpeditionActivity
import com.jh.roachecklist.ui.dialog.DialogUtil
import com.jh.roachecklist.utils.CheckListUtil
import com.jh.roachecklist.utils.DefaultNotification
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CharacterActivity : BaseActivity<ActivityCharacterBinding, CharacterViewModel>() {

    companion object {

        const val EXTRA_LEVEL = "EXTRA_LEVEL"
        const val EXTRA_NICK_NAME = "EXTRA_NICK_NAME"
        const val EXTRA_POSITION = "EXTRA_POSITION"

    }

    @Inject lateinit var pref: AppPreference
    @Inject lateinit var checkListUtil: CheckListUtil

    override val viewModel: CharacterViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_character

    private val characterAdapter: CharacterAdapter by lazy { CharacterAdapter( startCheckList, longClickListener ) }

    override fun initViewAndEvent() {

        setSupportActionBar( dataBinding.tbCharacter, false )
        val adRequest = AdRequest.Builder().build()
        dataBinding.adView.loadAd( adRequest )

        dataBinding.rvCharacter.apply {

            layoutManager = GridLayoutManager(this@CharacterActivity, 2)
            adapter = characterAdapter

        }

        viewModel.clickReset.observe( this, {

            DialogUtil.showResetDialog( this, layoutInflater ) {

                checkListUtil.resetCheckList()

                val packageManager: PackageManager = packageManager
                val intent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
                val componentName = intent?.component
                val mainIntent = Intent.makeRestartActivityTask(componentName)
                startActivity(mainIntent)
                System.runFinalization()

            }

        })

        viewModel.originalList.observe( this, {

            viewModel.setRvItems( it )

        })

        viewModel.clickAddCharacter.observe( this, {

            DialogUtil.showAddCharacterDialog( this, layoutInflater ) { name: String, level: Int, klass: String, favorite: Int ->
                viewModel.addCharacter( name, level, klass, favorite )
            }

        })

        viewModel.clickSetting.observe( this, {

            DialogUtil.showSettingDialog( this, layoutInflater, pref, settingAlarm, DefaultNotification.NOTIFICATION_CODE_DEFAULT )

        })

        viewModel.rvItems.observe( this, {

            characterAdapter.submitList( it.toList() )

        })

        viewModel.event.observe( this, { event ->

            when ( event ) {

                CharacterViewModel.CharacterEvent.EXIST -> { Toast.makeText( this, "이미 존재하는 캐릭터 입니다.", Toast.LENGTH_SHORT).show() }
                else -> {}

            }

        })

        viewModel.clickExpedition.observe( this, {

            Intent( this, ExpeditionActivity::class.java ).apply {

                val optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation( this@CharacterActivity )
                startActivity( this, optionsCompat.toBundle() )

            }

        })

    }

    private val resultForCharacter = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {

        val pos = it.data?.getIntExtra( CheckListActivity.RESULT_POSITION, 999) ?: 9999
        viewModel.updateSuccessState( pos )
        characterAdapter.notifyItemChanged( pos )

    }

    private val startCheckList: Function1<Int, Unit> = { pos: Int ->

        val item = characterAdapter.currentList[pos]
        Intent( this, CheckListActivity::class.java ).apply {

            putExtra( EXTRA_LEVEL, item.level )
            putExtra( EXTRA_NICK_NAME, item.nickName )
            putExtra( EXTRA_POSITION, pos )

            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation( this@CharacterActivity )

            resultForCharacter.launch( this, optionsCompat )

        }

    }

    private val longClickListener = { pos: Int ->

        val item = characterAdapter.currentList[pos]
        DialogUtil.showEditMenuDialog( this, layoutInflater, item, updateCharacter, deleteCharacter, editType )

    }

    private val updateCharacter = { character: CharacterEntity, level: Int ->

        viewModel.updateCharacter( character, level )
        characterAdapter.notifyItemChanged( characterAdapter.currentList.indexOf( character ) )

    }

    private val deleteCharacter = { character: CharacterEntity ->

        viewModel.deleteCharacter( character )

    }

    private val settingAlarm = { hour: Int, minute: Int, triggerTime: Long, alarmManager: AlarmManager, pendingIntent: PendingIntent ->

        alarmManager.cancel( pendingIntent )
        val realTriggerTime = if ( triggerTime > System.currentTimeMillis() )
            triggerTime
        else
            triggerTime + Const.INTERVAL
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, realTriggerTime, AlarmManager.INTERVAL_DAY, pendingIntent)
        viewModel.saveTime( hour, minute )

    }

    private val editType = { characterEntity: CharacterEntity, favorite: Int ->

        viewModel.editFavoriteType( characterEntity, favorite )
        characterAdapter.notifyItemChanged( characterAdapter.currentList.indexOf( characterEntity ) )

    }

}
