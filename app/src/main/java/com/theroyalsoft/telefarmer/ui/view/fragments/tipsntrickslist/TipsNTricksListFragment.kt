package com.theroyalsoft.telefarmer.ui.view.fragments.tipsntrickslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmer.primary.network.model.home.TricksTip
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentTipsNTricksListBinding
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.RecyclerViewItemDecoration
import com.theroyalsoft.telefarmer.ui.adapters.tipsntricks.TipsNTricksAdapter
import com.theroyalsoft.telefarmer.ui.view.fragments.newsdetails.NewsViewModel
import com.theroyalsoft.telefarmer.utils.isInvisible
import com.theroyalsoft.telefarmer.utils.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class TipsNTricksListFragment : Fragment() {

    private lateinit var binding: FragmentTipsNTricksListBinding
    private val viewModel: NewsViewModel by viewModels()

    private val args: TipsNTricksListFragmentArgs by navArgs()
    private var id: Int = -1
    private var name: String = ""

    private lateinit var mTipsNTricksAdapter: TipsNTricksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (arguments != null) {
            id = args.id
            name = args.name
        }
        binding = FragmentTipsNTricksListBinding.inflate(layoutInflater, container, false)
        initView()
        event()
        viewModel.getHome()
        getHomeResponse()
        ifApiGetError()

        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            imgLeft.isInvisible()
            tvToolbarTitle.text = if (id == -1) {
                getString(R.string.all)
            } else name
            tvToolbarSubtitle.isVisible()
            tvToolbarSubtitle.text = getString(R.string.tips_n_tricks)
        }

        mTipsNTricksAdapter = TipsNTricksAdapter {
            //Item Click
            val action =
                TipsNTricksListFragmentDirections.actionTipsNTricksListFragmentToTipsNTricksDetailsFragment(
                    it
                )
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

    private fun getHomeResponse() {
        lifecycleScope.launch {
            viewModel._homeStateFlow.collect {
                val list = it.static.tricks_tips
                if (id == -1) {
                    mTipsNTricksAdapter.submitData(list)
                } else {
                    mTipsNTricksAdapter.submitData(list.filter { data -> id == data.category_id })
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