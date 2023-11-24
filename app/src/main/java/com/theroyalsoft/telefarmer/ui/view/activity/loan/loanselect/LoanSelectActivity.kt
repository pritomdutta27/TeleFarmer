package com.theroyalsoft.telefarmer.ui.view.activity.loan.loanselect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityLoanSelectBinding
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.bottomlist.ListBottomSheetFragment
import com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet.LoadDetailsBottomSheet
import com.theroyalsoft.telefarmer.utils.JsonUtils.getCropDetails
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import com.theroyalsoft.telefarmer.utils.isInvisible

class LoanSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanSelectBinding
    private lateinit var mLoadDetailsBottomSheet: LoadDetailsBottomSheet
    private lateinit var mListBottomSheetFragment: ListBottomSheetFragment

    private var mLoanDetailsResponseItem: LoanDetailsResponseItem? = null

    companion object {
        @JvmStatic
        fun newIntent(context: Context, phone: String): Intent =
            Intent(context, LoanSelectActivity::class.java).putExtra("phone", phone)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        binding = ActivityLoanSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        event()
    }

    private fun initView() {
        mLoadDetailsBottomSheet = LoadDetailsBottomSheet()
        mListBottomSheetFragment = ListBottomSheetFragment()
        binding.apply {
            toolBarLay.btnBack.setOnClickListener { finish() }
            toolBarLay.imgLeft.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.loan)
        }
    }

    private fun event() {
        binding.apply {
            clBtnLoanSubmit.setOnClickListener {
                if (mLoanDetailsResponseItem == null) {
                    showToast(getString(R.string.select_crop_type))
                    return@setOnClickListener
                } else if (etLand.text.isNullOrEmpty()) {
                    showToast(getString(R.string.amount_of_land))
                    return@setOnClickListener
                }
                if (!mLoadDetailsBottomSheet.isAdded) {
                    mLoadDetailsBottomSheet.showNow(supportFragmentManager, "ListBottomFragment")
                }
                mLoadDetailsBottomSheet.setData(
                    mLoanDetailsResponseItem,
                    etLand.text.toString().toFloat()
                )
            }

            etCropType.setOnClickListener {
                if (!mListBottomSheetFragment.isAdded) {
                    mListBottomSheetFragment.showNow(supportFragmentManager, "ListBottomFragment")
                }
                mListBottomSheetFragment.submitData(getCrop(), "ফসলের তালিকা") { data ->
                    etCropType.text = data.crop_name
                    mLoanDetailsResponseItem = data
                }
            }
        }
    }

    fun getCrop(): List<LoanDetailsResponseItem> {
        return getCropDetails("load_data.json")
    }
}