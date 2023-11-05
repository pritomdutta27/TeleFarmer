package com.theroyalsoft.telefarmer.ui.view.activity.splash


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.farmer.primary.network.utils.AppConstants.PREF_KEY_USER_LOGGED_IN_MODE
import com.theroyalsoft.telefarmer.databinding.ActivitySplashBinding
import com.theroyalsoft.telefarmer.ui.view.activity.login.LoginActivity
import com.theroyalsoft.telefarmer.ui.view.activity.main.MainActivity
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import com.theroyalsoft.telefarmer.utils.hideNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var pref: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        hideNavigationBar()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() //add this
        fadeIn.duration = 2000
        binding.imgAppIcon.animation = fadeIn
        Handler(Looper.getMainLooper()).postDelayed({
            callActivity()
        }, 3000)
    }

    fun callActivity() {
        lifecycleScope.launch {
            val userLogin = pref.getInt(PREF_KEY_USER_LOGGED_IN_MODE)
            Log.e("userLogin", "userLogin ${userLogin}")
            if (userLogin == null || userLogin == LOGGED_IN_MODE_LOGGED_OUT.type) {
                pref.putInt(
                    PREF_KEY_USER_LOGGED_IN_MODE,
                    LOGGED_IN_MODE_LOGGED_OUT.type
                )
                startActivity(LoginActivity.newIntent(this@SplashActivity))
                finish()
            } else {
                startActivity(MainActivity.newIntent(this@SplashActivity))
                finish()
            }
        }

    }
}