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
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jh.roachecklist.App
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
import com.jh.roachecklist.ui.dialog.DialogProgress
import com.jh.roachecklist.ui.dialog.DialogUtil
import com.jh.roachecklist.utils.AppOpenManager
import com.jh.roachecklist.utils.CheckListUtil
import com.jh.roachecklist.utils.DefaultNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import java.util.*
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

    private lateinit var toast: Toast
    private var backKeyPressedTime: Long = 0

    override val viewModel: CharacterViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_character

    private val characterAdapter: CharacterAdapter by lazy { CharacterAdapter( startCheckList, longClickListener ) }

    private val appOpenManager: AppOpenManager by lazy { (application as App).appOpenManager }

    private var count = 0
    private var frontCount = 0

    private var mInterstitialAd: InterstitialAd? = null

    override fun initViewAndEvent() {

        setSupportActionBar( dataBinding.tbCharacter, false )
        val adRequest = AdRequest.Builder().build()
        dataBinding.adView.loadAd( adRequest )

        dataBinding.rvCharacter.apply {

            layoutManager = GridLayoutManager(this@CharacterActivity, 2)
            adapter = characterAdapter

        }

        InterstitialAd.load(this, getString(R.string.front_ad_unit_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("TAG", adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("TAG", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("TAG", "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("TAG", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("TAG", "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }

        viewModel.clickMenu.observe( this, {

            dataBinding.drawerNav.openDrawer(GravityCompat.END)

        })

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

        viewModel.startActivity.observe( this, {

            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation( this@CharacterActivity )
            startActivity( Intent( this, it.java ), optionsCompat.toBundle() )

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

            frontCount++

            lifecycleScope.launch {

//                if (  mInterstitialAd != null && getRandomNumber( frontCount ) ) {
//                    showProgress()
//                    delay(1000)
//                    dismissProgress()
//                }

                putExtra(EXTRA_LEVEL, item.level)
                putExtra(EXTRA_NICK_NAME, item.nickName)
                putExtra(EXTRA_POSITION, pos)

                val optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@CharacterActivity)

                resultForCharacter.launch(this@apply, optionsCompat)

                if ( mInterstitialAd != null && getRandomNumber( frontCount ) ) {
//                    showProgress()
//                    delay(1000)
//                    dismissProgress()
                    mInterstitialAd?.show(this@CharacterActivity)
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }

            }

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

    override fun onBackPressed() {
        if (dataBinding.drawerNav.isDrawerOpen(GravityCompat.END))
            dataBinding.drawerNav.closeDrawer(GravityCompat.END)
        else {

            if (System.currentTimeMillis() > backKeyPressedTime + 1500) {
                backKeyPressedTime = System.currentTimeMillis()
                toast = Toast.makeText(this, "뒤로가기를 한 번 더 누르면 종료 됩니다.", Toast.LENGTH_SHORT)
                toast.show()
                return
            }

            if (System.currentTimeMillis() <= backKeyPressedTime + 1500) {
                finish()
                toast.cancel()
            }

        }

    }

//    override fun onResume() {
//        super.onResume()
//        if ( count == 0)
//            appOpenManager.showAdIfAvailable()
//        count++
//        frontCount++
//    }

    private fun getRandomNumber(count: Int ): Boolean {

        val random = Random()
        val num = random.nextInt(6)
        return num + count > 6

    }

}
