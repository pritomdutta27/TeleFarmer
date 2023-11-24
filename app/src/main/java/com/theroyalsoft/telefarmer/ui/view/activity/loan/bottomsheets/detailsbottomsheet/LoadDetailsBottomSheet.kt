package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentLoadDetailsBottomSheetBinding
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet.adapter.LoanDetailsAdapter
import com.theroyalsoft.telefarmer.ui.view.activity.loan.loansuccess.LoanSuccessActivity


class LoadDetailsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLoadDetailsBottomSheetBinding

    private lateinit var mLoanDetailsAdapter: LoanDetailsAdapter

    private var mLoanDetailsResponseItem: LoanDetailsResponseItem? = null
    private var amountOfLand: Float? = 0.0f
    private var totalAmount: Float = 0.0f

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        binding = FragmentLoadDetailsBottomSheetBinding.inflate(layoutInflater, container, false)

        initView()
        event()
        return binding.root
    }

    private fun initView() {
        mLoanDetailsAdapter = LoanDetailsAdapter()

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvLoadDetailsList.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(20))
            adapter = mLoanDetailsAdapter
        }
    }

    private fun event() {
        binding.apply {
            btnSubmit.setOnClickListener {
                startActivity(LoanSuccessActivity.newIntent(requireContext()))
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    fun setData(data: LoanDetailsResponseItem?, amountOfLand: Float) {
        mLoanDetailsResponseItem = data
        this.amountOfLand = amountOfLand
        totalAmount = 0f
        mLoanDetailsResponseItem?.crop_cost?.forEach {
            it.price = (it.price.toFloat() * amountOfLand).toString()
            totalAmount += it.price.toFloat()
        }

        initView()
        event()

        binding.tvTotalLoanAmount.text = "${totalAmount} টাকা"

        mLoanDetailsResponseItem?.crop_cost?.let {
            mLoanDetailsAdapter.submitData(it)
        }

    }
}