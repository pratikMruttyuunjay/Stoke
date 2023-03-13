package com.mruttyuunjay.stoke

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mruttyuunjay.stoke.databinding.ActivityPinBinding
import com.mruttyuunjay.stoke.security.Prefs
import com.mruttyuunjay.stoke.security.SecureActivityDelegate
import com.mruttyuunjay.stoke.utils.KtPasscodeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinActivity : AppCompatActivity() {

    lateinit var binding: ActivityPinBinding
    private val prefs = Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passcodeView.apply {

            setLocalPasscode(prefs.getBootPin().toString())
            binding.passcodeView.listener = object : KtPasscodeView.PasscodeViewListener {

                override fun onFail(wrongNumber: String?) {
                    Toast.makeText(context, "Wrong Pin!!", Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(number: String?) {
                    SecureActivityDelegate.unlock()
                    val data = Intent().apply {
                        putExtra("#SettingFrag", "InitResetPwd")
                    }
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}