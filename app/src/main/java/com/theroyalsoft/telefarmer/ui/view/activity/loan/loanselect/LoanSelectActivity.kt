package com.theroyalsoft.telefarmer.ui.view.activity.loan.loanselect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityLoanSelectBinding
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import com.theroyalsoft.telefarmer.utils.isInvisible

class LoanSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanSelectBinding

    companion object {
        @JvmStatic
        fun newIntent(context: Context, phone: String): Intent =
            Intent(context, LoanSelectActivity::class.java)
                .putExtra("phone", phone)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        binding = ActivityLoanSelectBinding.inflate(layoutInflater)
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