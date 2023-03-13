package com.mruttyuunjay.stoke

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.mruttyuunjay.stoke.security.Prefs
import com.mruttyuunjay.stoke.security.SecureActivityDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), DefaultLifecycleObserver {

    private val pref = Prefs
    override fun onCreate() {
        super<Application>.onCreate()
        pref.init(this)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        pref.setUseAuthenticator(true)
        pref.setSecureScreen("ALWAYS")
        pref.setBootPin(5050)
    }

    override fun onStart(owner: LifecycleOwner) {
        SecureActivityDelegate.onApplicationStart()
    }

    override fun onStop(owner: LifecycleOwner) {
        SecureActivityDelegate.onApplicationStopped()
    }

}