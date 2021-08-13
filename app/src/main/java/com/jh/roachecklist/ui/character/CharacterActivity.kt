package com.jh.roachecklist.ui.character

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterBinding
import com.jh.roachecklist.db.CharacterEntity
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
        const val EXTRA_HIGHEST_LEVEL = "EXTRA_HIGHEST_LEVEL"

    }

    @Inject lateinit var checkListUtil: CheckListUtil

    override val viewModel: CharacterViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_character

    private val characterAdapter: CharacterAdapter by lazy { CharacterAdapter( startCheckList, longClickListener ) }

    override fun initViewAndEvent() {

        setSupportActionBar( dataBinding.tbCharacter, false )

        dataBinding.rvCharacter.apply {

            layoutManager = GridLayoutManager( this@CharacterActivity, 2 )
            adapter = characterAdapter

        }

        viewModel.originalList.observe( this, {

            viewModel.setRvItems( it )

        })

        viewModel.clickAddCharacter.observe( this, {

            DialogUtil.showAddCharacterDialog( this, layoutInflater ) { name: String, level: Int, klass: String, favorite: Int ->
                viewModel.addCharacter( name, level, klass, favorite )
            }

        })

        viewModel.clickSetting.observe( this, {

            DialogUtil.showSettingDialog( this, layoutInflater, settingAlarm, DefaultNotification.NOTIFICATION_CODE_DEFAULT )

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

                putExtra( EXTRA_HIGHEST_LEVEL, viewModel.getHighestLevel())
                Log.i("zxcv","level :: ${viewModel.getHighestLevel()}")
                val optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation( this@CharacterActivity )
                startActivity( this, optionsCompat.toBundle() )

            }

        })

        dataBinding.btnTest.setOnClickListener {

            checkListUtil.alarmRaid()
            checkListUtil.alarmWeekly()
            checkListUtil.alarmDaily()
//            checkListUtil.alarmExpedition()

        }

    }

    private val startCheckList: Function1<Int, Unit> = { pos: Int ->

        val item = characterAdapter.currentList[pos]
        Intent( this, CheckListActivity::class.java ).apply {

            putExtra( EXTRA_LEVEL, item.level )
            putExtra( EXTRA_NICK_NAME, item.nickName )

            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation( this@CharacterActivity )

            startActivity( this, optionsCompat.toBundle() )

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

    private val settingAlarm = { triggerTime: Long, alarmManager: AlarmManager, pendingIntent: PendingIntent ->

        alarmManager.cancel( pendingIntent )
        Log.i("zxcv","세팅알람")
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)

    }

    private val editType = { characterEntity: CharacterEntity, favorite: Int ->

        viewModel.editFavoriteType( characterEntity, favorite )
        characterAdapter.notifyItemChanged( characterAdapter.currentList.indexOf( characterEntity ) )

    }

}
