package com.theroyalsoft.telefarmer.ui.view.fragments.uploadimg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentUploadImgBinding
import com.theroyalsoft.telefarmer.ui.adapters.uploadimg.UploadImageAdapter


class UploadImgFragment : Fragment() {

    private lateinit var binding: FragmentUploadImgBinding

    private lateinit var mUploadImageAdapter: UploadImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadImgBinding.inflate(layoutInflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.tvToolbarTitle.text = getString(R.string.images)

        mUploadImageAdapter = UploadImageAdapter {
            //Item Click
            val action = UploadImgFragmentDirections.actionUploadImgFragmentToPreviousConsultationFragment()
            findNavController().navigate(action)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvUploadImg.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = mUploadImageAdapter
        }
    }
}