package com.theroyalsoft.telefarmer.ui.view.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.farmer.primary.network.model.login.LoginParams
import com.theroyalsoft.telefarmer.extensions.getPhoneDeviceId
import com.theroyalsoft.telefarmer.extensions.hide
import com.theroyalsoft.telefarmer.extensions.hideKeyboard
import com.theroyalsoft.telefarmer.extensions.isMobileNumberValid
import com.theroyalsoft.telefarmer.extensions.show
import com.theroyalsoft.telefarmer.extensions.showFromStringToast
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.BuildConfig
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityLoginBinding
import com.theroyalsoft.telefarmer.ui.view.activity.main.MainActivity
import com.theroyalsoft.telefarmer.ui.view.activity.otp.OTPActivity
import com.theroyalsoft.telefarmer.ui.view.activity.reg.RegActivity
import com.theroyalsoft.telefarmer.utils.hideNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNavigationBar()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event()

        //Api Call
        getResponseLogin()
        ifApiGetError()
    }

    private fun event() {

        if (BuildConfig.DEBUG) {
            binding.etPhoneNumber.setText("01844476993")
        }
        binding.btnSend.setOnClickListener {
            if (!binding.etPhoneNumber.text.toString().isMobileNumberValid()) {
                this.showFromStringToast(R.string.valid_mobile_number)
                return@setOnClickListener
            }

            val params = LoginParams(
                channel = "android",
                deviceId = this.getPhoneDeviceId(),
                phoneNumber = binding.etPhoneNumber.text.toString(),
                type = "newUser",
                userType = "patient"
            )
            showLoading()
            hideKeyboard(binding.btnSend)

            viewModel.submitLogin(params)
        }
    }

    private fun getResponseLogin() {
        lifecycleScope.launch {
            viewModel._loginStateFlow.collect { data ->
                withContext(Dispatchers.Main) {

                    if (data.isSuccess) {
                        if ((!data.isProfile && !data.isVerified) || (data.isProfile && !data.isVerified)) {
                            delay(300)
                            hideLoading()
                            openOtp(data.isProfile)
                        }else if (data.isProfile && data.isVerified){
                            delay(1000)
                            hideLoading()
                            viewModel.setLogin(accessToken = data.accessToken, phone = binding.etPhoneNumber.text.toString())
                            openMain()
                        }
                        else {
                            delay(100)
                            hideLoading()
                            openReg(
                                phoneNum = binding.etPhoneNumber.text.toString(),
                                accessToken = data.accessToken
                            )
                        }
                    } else {
                        showToast(data.message)
                    }
                }
            }
        }
    }

    private fun openMain() {
        startActivity(MainActivity.newIntent(this))
    }

    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                hideLoading()
                withContext(Dispatchers.Main) {
                    if (errorStr.isNotEmpty()) {
                        applicationContext.showToast(errorStr)
                    }
                }
            }
        }
    }

    private fun openOtp(isProfile: Boolean) {
        startActivity(OTPActivity.newIntent(this, binding.etPhoneNumber.text.toString(), isProfile))
    }

    private fun openReg(phoneNum: String, accessToken: String) {
        startActivity(RegActivity.newIntent(this, phone = phoneNum, accessToken = accessToken))
    }

    private fun showLoading() {
        binding.apply {
            etPhoneNumber.isEnabled = false
            btnSend.hide()
            progressCircular.show()
        }
    }

    private fun hideLoading() {
        binding.apply {
            etPhoneNumber.isEnabled = true
            btnSend.show()
            progressCircular.hide()
        }
    }
}