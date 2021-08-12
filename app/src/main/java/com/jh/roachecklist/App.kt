package com.jh.roachecklist

import android.app.Application
import com.jh.roachecklist.preference.AppPreference
import com.jh.roachecklist.repository.Repository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var pref: AppPreference
    @Inject
    lateinit var repository: Repository

    override fun onCreate() {

        super.onCreate()
        pref.getPref()
        if ( pref.isFirst ) {

            CoroutineScope( Dispatchers.IO).launch {

                repository.insertCheckList()
                pref.isFirst = false

            }

        }

    }

}