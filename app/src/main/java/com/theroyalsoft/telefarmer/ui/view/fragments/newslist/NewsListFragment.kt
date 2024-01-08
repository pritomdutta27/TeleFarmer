package com.theroyalsoft.telefarmer.ui.view.fragments.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentNewsListragmentBinding
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.ui.adapters.news.NewsAdapter
import com.theroyalsoft.telefarmer.ui.view.fragments.newsdetails.NewsViewModel
import com.theroyalsoft.telefarmer.utils.isInvisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListragmentBinding
    private val viewModel: NewsViewModel by viewModels()

    private lateinit var aNewsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListragmentBinding.inflate(layoutInflater, container, false)

        initView()
        event()

        viewModel.getHome()

        getHomeResponse()
        ifApiGetError()

        return binding.root
    }

    private fun initView() {
        binding.apply {
            toolBarLay.btnBack.setOnClickListener { findNavController().popBackStack() }
            toolBarLay.imgLeft.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.news)
        }

        aNewsAdapter = NewsAdapter {
            //Item Click
            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(it)
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

    private fun getHomeResponse() {
        lifecycleScope.launch {
            viewModel._homeStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    data.News?.let {
                        aNewsAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                withContext(Dispatchers.Main) {
                    if (errorStr.isNotEmpty()) {
                        requireContext().showToast(errorStr)
                    }
                }
            }
        }
    }
}