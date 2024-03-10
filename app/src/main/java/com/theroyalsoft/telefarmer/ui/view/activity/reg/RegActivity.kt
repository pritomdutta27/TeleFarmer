package com.theroyalsoft.telefarmer.ui.view.activity.reg

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import bio.medico.patient.common.DeviceIDUtil
import bio.medico.patient.model.apiResponse.RequestPatientUpdate
import bio.medico.patient.model.apiResponse.RequestSignUp
import com.skh.hkhr.util.log.ToastUtil
import com.theroyalsoft.telefarmer.databinding.ActivityRegBinding
import com.theroyalsoft.telefarmer.extensions.getFromDateTime
import com.theroyalsoft.telefarmer.extensions.getTimeForDobUiToApi
import com.theroyalsoft.telefarmer.extensions.hideKeyboard
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.extensions.showLoadingDialog
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.ui.custom.DatePickerFragment
import com.theroyalsoft.telefarmer.ui.view.activity.main.MainActivity
import com.theroyalsoft.telefarmer.ui.view.activity.otp.OTPViewModel
import com.theroyalsoft.telefarmer.utils.hideNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding
    private val viewModel: OTPViewModel by viewModels()

    private lateinit var datePickerDialog: DatePickerFragment
    private lateinit var loading: Dialog

    companion object {
        @JvmStatic
        fun newIntent(context: Context, phone: String, accessToken: String): Intent =
            Intent(context, RegActivity::class.java)
                .putExtra("phone", phone)
                .putExtra("accessToken", accessToken)
    }

    private var phoneNum = ""
    private var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNavigationBar()
        phoneNum = intent.getStringExtra("phone") ?: ""
        accessToken = intent.getStringExtra("accessToken") ?: ""

        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        initUI()
        event()

        getResponse()
        ifApiGetError()
    }

    private fun initUI() {
        loading = showLoadingDialog()
        datePickerDialog = DatePickerFragment()
    }

    private fun event() {
        binding.apply {
            etDob.setSafeOnClickListener {
                clearFocus()
                if (!datePickerDialog.isAdded) {
                    datePickerDialog.showNow(supportFragmentManager, "DatePicker")
                }
            }

            btnSubmit.setSafeOnClickListener {
                if (binding.etName.text.toString().isEmpty()) {
                    ToastUtil.showToastMessage("Name can't be empty!")
                    binding.etName.requestFocus()
                    return@setSafeOnClickListener
                } else if (binding.etDob.text.toString().isEmpty()) {
                    binding.etDob.error = "Please enter valid Date of birth!"
                    binding.etDob.requestFocus()
                    return@setSafeOnClickListener
                }

//                val requestPatientUpdate = RequestPatientUpdate(
//                    name = binding.etName.text.toString(),
//                    dob = binding.etDob.text.toString(),
//                    weight = "66",
//                    height = "5:5",
//                    location = "",
//                    image = ""
//                )

                val requestPatientUpdate = RequestSignUp(
                    binding.etName.text.toString().trim(),
                    phoneNum,
                    phoneNum,
                    phoneNum,
                    "android",
                    "no",
                    DeviceIDUtil.getDeviceID(),
                    binding.etDob.text.toString()
                )
                hideKeyboard(binding.btnSubmit)
                loading.show()
                viewModel.requestReg(
                    requestPatientUpdate,
                    accessToken = accessToken,
                    phone = phoneNum
                )
            }
        }

        datePickerDialog.setOnClick {
            clearFocus()
            binding.etDob.text = it.getFromDateTime("yyyy/MM/dd", "MMMM dd, yyyy")
        }
    }

    private fun clearFocus() {
        binding.apply {
            etName.clearFocus()
        }
    }

    //TODO: API
    private fun getResponse() {
        lifecycleScope.launch {
            viewModel._updateStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    if (data) {
                        openMain()
                    } else {
                        //showToast(data.message)
                    }
                }
            }
        }
    }

    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                withContext(Dispatchers.Main) {
                    loading.hide()
                    if (errorStr.isNotEmpty()) {
                        showToast(errorStr)
                    }
                }
            }
        }
    }

    private fun openMain() {
        startActivity(MainActivity.newIntent(this))
    }
}