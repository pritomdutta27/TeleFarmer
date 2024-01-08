package com.theroyalsoft.telefarmer.ui.view.fragments.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.model.home.NewsModel
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentNewsDetailsBinding
import com.theroyalsoft.telefarmer.extensions.setImage
import com.theroyalsoft.telefarmer.utils.isInvisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()
    private var newsDetails: NewsModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if (arguments != null) {
            newsDetails = args.newData
        }
        binding = FragmentNewsDetailsBinding.inflate(layoutInflater, container, false)

        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            toolBarLay.btnBack.setOnClickListener { findNavController().popBackStack() }
            toolBarLay.imgLeft.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.news_details)

            val urlImg = LocalData.getMetaInfoMetaData().imgBaseUrl + "/uploaded/" + newsDetails?.imageUrl
            imgNews.setImage(urlImg)
            tvNewTitle.text = newsDetails?.titleBn
            tvNewDetails.text = newsDetails?.detailsBn
            tvNewDate.text = newsDetails?.dateAndTime
        }

    }
}