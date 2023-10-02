package com.theroyalsoft.telefarmer.ui.view.fragments.tipsntricksdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentTipsNTricksDetailsBinding
import com.theroyalsoft.telefarmer.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TipsNTricksDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTipsNTricksDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTipsNTricksDetailsBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.tvToolbarTitle.text = "Fish"
        binding.toolBarLay.tvToolbarSubtitle.isVisible()
        binding.toolBarLay.tvToolbarSubtitle.text =getString(R.string.tips_n_tricks)
    }
}