package com.jh.roachecklist.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.jh.roachecklist.R
import com.jh.roachecklist.ui.character.CharacterActivity
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launchWhenStarted {

            delay( 1000 )
            Intent( this@SplashActivity, CharacterActivity::class.java ).apply {

                startActivity( this )
                finish()

            }

        }

    }
}