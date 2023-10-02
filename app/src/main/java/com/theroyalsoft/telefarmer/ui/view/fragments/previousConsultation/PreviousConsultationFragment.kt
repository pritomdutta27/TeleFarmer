package com.theroyalsoft.telefarmer.ui.view.fragments.previousConsultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentPreviousConsultationBinding
import com.theroyalsoft.telefarmer.ui.adapters.previousConsultation.PreviousConsultationAdapter


class PreviousConsultationFragment : Fragment() {

    private lateinit var binding: FragmentPreviousConsultationBinding

    private lateinit var mPreviousConsultationAdapter: PreviousConsultationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreviousConsultationBinding.inflate(layoutInflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.tvToolbarTitle.text = getString(R.string.previous_consultation)

        mPreviousConsultationAdapter = PreviousConsultationAdapter()

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvPreviousConsultation.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = mPreviousConsultationAdapter
        }
    }
}