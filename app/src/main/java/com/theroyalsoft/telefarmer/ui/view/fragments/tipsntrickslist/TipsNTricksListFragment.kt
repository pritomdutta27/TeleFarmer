package com.theroyalsoft.telefarmer.ui.view.fragments.tipsntrickslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.helper.RecyclerViewItemDecoration
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentTipsNTricksListBinding
import com.theroyalsoft.telefarmer.ui.adapters.tipsntricks.TipsNTricksAdapter
import com.theroyalsoft.telefarmer.ui.view.fragments.newslist.NewsListFragmentDirections
import com.theroyalsoft.telefarmer.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TipsNTricksListFragment : Fragment() {

    private lateinit var binding: FragmentTipsNTricksListBinding

    private lateinit var mTipsNTricksAdapter: TipsNTricksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTipsNTricksListBinding.inflate(layoutInflater, container, false)
        initView()
        event()
        return binding.root
    }

    private fun initView() {

        binding.toolBarLay.tvToolbarTitle.text = getString(R.string.all)
        binding.toolBarLay.tvToolbarSubtitle.isVisible()
        binding.toolBarLay.tvToolbarSubtitle.text = getString(R.string.tips_n_tricks)

        mTipsNTricksAdapter = TipsNTricksAdapter {
            //Item Click
            val action = TipsNTricksListFragmentDirections.actionTipsNTricksListFragmentToTipsNTricksDetailsFragment(0)
            findNavController().navigate(action)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvTipsNTricks.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(RecyclerViewItemDecoration(20, context))
            adapter = mTipsNTricksAdapter
        }
    }

    private fun event() {

    }

}