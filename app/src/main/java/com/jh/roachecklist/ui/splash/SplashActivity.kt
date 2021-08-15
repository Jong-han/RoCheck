package com.jh.roachecklist.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.MobileAds
import com.jh.roachecklist.R
import com.jh.roachecklist.ui.character.CharacterActivity
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launchWhenStarted {

            MobileAds.initialize(this@SplashActivity) {}

            delay( 1000 )
            Intent( this@SplashActivity, CharacterActivity::class.java ).apply {

                startActivity( this )
                finish()

            }

        }

    }
}