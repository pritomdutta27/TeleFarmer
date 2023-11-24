package com.theroyalsoft.telefarmer.ui.view.activity.loan.loansuccess

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityLoanSuccessBinding
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import com.theroyalsoft.telefarmer.utils.isInvisible

class LoanSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanSuccessBinding

    companion object {
        @JvmStatic
        fun newIntent(context: Context): Intent =
            Intent(context, LoanSuccessActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        binding = ActivityLoanSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.apply {
            toolBarLay.btnBack.setOnClickListener { finish() }
            toolBarLay.imgLeft.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.loan)
        }
    }
}