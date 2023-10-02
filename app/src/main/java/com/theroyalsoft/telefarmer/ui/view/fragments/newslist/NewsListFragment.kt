package com.theroyalsoft.telefarmer.ui.view.fragments.newslist

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
import com.theroyalsoft.telefarmer.databinding.FragmentNewsListragmentBinding
import com.theroyalsoft.telefarmer.ui.adapters.news.NewsAdapter
import com.theroyalsoft.telefarmer.ui.view.fragments.newsdetails.NewsDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListragmentBinding

    private lateinit var aNewsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListragmentBinding.inflate(layoutInflater, container, false)

        initView()
        event()

        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.tvToolbarTitle.text = getString(R.string.news)

        aNewsAdapter = NewsAdapter {
            //Item Click
            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(0)
            findNavController().navigate(action)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvNews.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = aNewsAdapter
        }
    }

    private fun event() {
//        aNewsAdapter.onIte
    }
}