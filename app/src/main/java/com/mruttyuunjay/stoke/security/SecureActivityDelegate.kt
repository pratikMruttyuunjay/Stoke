package com.mruttyuunjay.stoke.security

import android.content.Intent
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mruttyuunjay.stoke.PinActivity
import com.mruttyuunjay.stoke.security.AuthenticatorUtil.isAuthenticationSupported

interface SecureActivityDelegate {

    fun registerSecureActivity(activity: AppCompatActivity)

    companion object {
        private val preferences = Prefs

        /**
         * Set to true if we need the first activity to authenticate.
         *
         * Always require unlock if app is killed.
         */

        var requireUnlock = true

        fun onApplicationStopped() {
            Log.d("SecureActivityDelegate", "onApplicationStopped")

            if (!preferences.getUseAuthenticator()) return
            Log.wtf(
                "SecureActivityDelegatePreferences : getUseAuthenticator",
                preferences.getUseAuthenticator().toString()
            )

            if (!AuthenticatorUtil.isAuthenticating) {
                // Return if app is closed in locked state
                if (requireUnlock) return
                Log.wtf(
                    "SecureActivityDelegatePreferences : getLockAppAfter",
                    preferences.getLockAppAfter().toString()
                )
                Log.wtf(
                    "SecureActivityDelegatePreferences : getLastAppClosed",
                    preferences.getLastAppClosed().toString()
                )
                // Save app close time if lock is delayed
                if (preferences.getLockAppAfter() > 0) {
                    preferences.setLastAppClosed(System.currentTimeMillis())
                }
            }
        }

        /**
         * Checks if unlock is needed when app comes foreground.
         */
        fun onApplicationStart() {
            Log.d("SecureActivityDelegate", "onApplicationStart")

//            val preferences = Injekt.get<SecurityPreferences>()
            Log.wtf(
                "SecureActivityDelegatePreferences : getUseAuthenticator",
                preferences.getUseAuthenticator().toString()
            )

            if (!preferences.getUseAuthenticator()) return

            val lastClosedPref = preferences.getLastAppClosed()
            Log.wtf(
                "SecureActivityDelegatePreferences : getLastAppClosed",
                lastClosedPref.toString()
            )

            // `requireUnlock` can be true on process start or if app was closed in locked state
            if (!AuthenticatorUtil.isAuthenticating && !requireUnlock) {
                requireUnlock = when (val lockDelay = preferences.getLockAppAfter()) {
                    -1 -> false // Never
                    0 -> true // Always
                    else -> lastClosedPref + lockDelay * 60_000 <= System.currentTimeMillis()
                }
            }

            preferences.deleteLastAppClosed()
        }

        fun unlock() {
            requireUnlock = false
        }
    }
}


class SecureActivityDelegateImpl : SecureActivityDelegate, DefaultLifecycleObserver {

    private lateinit var activity: AppCompatActivity
    private val preferences = Prefs

//    private val preferences: BasePreferences by injectLazy()
//    private val securityPreferences: SecurityPreferences by injectLazy()

    override fun registerSecureActivity(activity: AppCompatActivity) {
        this.activity = activity
        Log.d("SecureActivityDelegate", "registerSecureActivity")
        activity.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        Log.d("SecureActivityDelegate", "onCreate")
        setSecureScreen()
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.d("SecureActivityDelegate", "onResume")
        setAppLock()
    }

    private fun setSecureScreen() {
        Log.d("#setSecureScreen", "setSecureScreen")
        val secureScreenFlow = preferences.getSecureScreen()
        val incognitoModeFlow = preferences.getIncognitoMode()
//        combine(secureScreenFlow, incognitoModeFlow) { secureScreen, incognitoMode ->
//            secureScreen == "ALWAYS" || secureScreen == "INCOGNITO" && incognitoMode
//        }
//            .onEach { activity.window.setSecureScreen(it) }
//            .launchIn(activity.lifecycleScope)
        if (secureScreenFlow == "ALWAYS" && incognitoModeFlow) {
            activity.window.setSecureScreen(true)
//            preferences.setSecureScreen("ALWAYS")
        } else {
            activity.window.setSecureScreen(false)
        }

        Log.wtf("SecureActivityDelegatePreferences : secureScreenFlow", secureScreenFlow.toString())
        Log.wtf(
            "SecureActivityDelegatePreferences : incognitoModeFlow",
            incognitoModeFlow.toString()
        )
    }

    private fun setAppLock() {
        Log.d("SecureActivityDelegate", "setAppLock")
        Log.wtf(
            "SecureActivityDelegatePreferences : getUseAuthenticator",
            preferences.getUseAuthenticator().toString()
        )
        Log.wtf(
            "SecureActivityDelegatePreferences : isAuthenticationSupported",
            activity.isAuthenticationSupported().toString()
        )

        if (!preferences.getUseAuthenticator()) return

        if (activity.isAuthenticationSupported()) {
            if (!SecureActivityDelegate.requireUnlock) return

            Log.wtf(
                "SecureActivityDelegatePreferences : requireUnlock",
                SecureActivityDelegate.requireUnlock.toString()
            )

            activity.startActivity(Intent(activity, PinActivity::class.java))
            activity.overridePendingTransition(0, 0)
        } else {
            preferences.setUseAuthenticator(false)
            Log.wtf(
                "Device not support Biometric lock so we don't use in build lock",
                preferences.getUseAuthenticator().toString()
            )
//            securityPreferences.useAuthenticator().set(false)
        }
    }
}


fun Window.setSecureScreen(enabled: Boolean) {
    if (enabled) {
        setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    } else {
        clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}
