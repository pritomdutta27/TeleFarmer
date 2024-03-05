package com.theroyalsoft.telefarmer.ui.view.activity.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import com.farmer.primary.network.model.otp.OtpParams
import com.theroyalsoft.telefarmer.extensions.getDeviceName
import com.theroyalsoft.telefarmer.extensions.getPhoneDeviceId
import com.theroyalsoft.telefarmer.extensions.hide
import com.theroyalsoft.telefarmer.extensions.setFormXmlText
import com.theroyalsoft.telefarmer.extensions.show
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityOtpactivityBinding
import com.theroyalsoft.telefarmer.ui.view.activity.main.MainActivity
import com.theroyalsoft.telefarmer.ui.view.activity.reg.RegActivity
import com.theroyalsoft.telefarmer.utils.hideNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class OTPActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun newIntent(context: Context, phone: String, isProfile: Boolean): Intent =
            Intent(context, OTPActivity::class.java)
                .putExtra("phone", phone)
                .putExtra("isProfile", isProfile)
    }

    private lateinit var binding: ActivityOtpactivityBinding

    private val viewModel: OTPViewModel by viewModels()

    private var phoneNum = ""
    private var accessToken = ""
    private var isProfile = true
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNavigationBar()
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phoneNum = intent.getStringExtra("phone") ?: ""
        isProfile = intent.getBooleanExtra("isProfile", true)

        binding.tvLoginSubtitle.text =
            setFormXmlText(R.string.please_type_the_four_digit_code, phoneNum)

        event()
        otpTimer()

        // Api Response
        getResponse()
        ifApiGetError()

    }

    private fun event() {
        binding.btnSubmit.setOnClickListener {
            if (binding.tvOtp.isEmpty()) {
                showToast(getString(R.string.enter_otp))
                return@setOnClickListener
            }
            val params = OtpParams(
                channel = "android",
                deviceId = this.getPhoneDeviceId(),
                phoneNumber = phoneNum,
                isTrusted = true,
                deviceName = this.getDeviceName(),
                otp = binding.tvOtp.otp.toString()
            )
            showLoading()
            viewModel.verify(params)
        }
    }

    private fun otpTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds = seconds % 60
                binding.tvSetTime.setTextColor(getColor(R.color.light_gray_date))
                binding.tvSetTime.text =
                    "Resend : " + String.format("%02d", minutes) + ":" + String.format(
                        "%02d",
                        seconds
                    )
            }

            override fun onFinish() {
                binding.tvSetTime.text = "Resend"
                binding.tvSetTime.tag = "send"
                binding.tvSetTime.setTextColor(getColor(R.color.red))
                this.cancel()
            }
        }
        timer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer = null
    }

    //TODO: API
    private fun getResponse() {
        lifecycleScope.launch {
            viewModel._otpStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    hideLoading()
                    if (data.isSuccess) {
                        if (isProfile) {
                            openMain()
                        } else {
                            if (data.accessToken.isNotEmpty()) {
                                accessToken = data.accessToken
                                openReg()
                            }
                        }
                    } else {
                        showToast(data.message.toString())
                    }
                }
            }
        }
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


    private fun openMain() {
        startActivity(MainActivity.newIntent(this))
    }

    private fun openReg() {
        startActivity(RegActivity.newIntent(this, phone = phoneNum, accessToken = accessToken))
    }

    private fun showLoading() {
        binding.apply {
            tvOtp.isEnabled = false
            btnSubmit.hide()
            progressCircular.show()
        }
    }

    private fun hideLoading() {
        binding.apply {
            tvOtp.isEnabled = true
            btnSubmit.show()
            progressCircular.hide()
        }
    }
}