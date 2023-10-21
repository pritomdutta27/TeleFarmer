package com.theroyalsoft.telefarmer.ui.view.fragments.tipsntricksdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farmer.primary.network.model.home.NewsModel
import com.farmer.primary.network.model.home.TricksTip
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentTipsNTricksDetailsBinding
import com.theroyalsoft.telefarmer.extensions.setImage
import com.theroyalsoft.telefarmer.ui.view.fragments.newsdetails.NewsDetailsFragmentArgs
import com.theroyalsoft.telefarmer.utils.isInvisible
import com.theroyalsoft.telefarmer.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TipsNTricksDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTipsNTricksDetailsBinding

    private val args: TipsNTricksDetailsFragmentArgs by navArgs()
    private var tricksTip: TricksTip? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            tricksTip = args.dataTips
        }
        binding = FragmentTipsNTricksDetailsBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            imgLeft.isInvisible()
            tvToolbarTitle.text = "Details"
            tvToolbarSubtitle.isVisible()
            tvToolbarSubtitle.text = getString(R.string.tips_n_tricks)
        }

        binding.apply {
            tvNewDetails.text = tricksTip?.details
            tvNewDetailsTitle.text = tricksTip?.title
            imgNews.setImage(tricksTip?.imageUrl ?: "")
        }

    }
}