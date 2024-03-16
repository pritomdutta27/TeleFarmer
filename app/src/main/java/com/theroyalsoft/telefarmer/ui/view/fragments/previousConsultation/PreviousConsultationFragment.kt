package com.theroyalsoft.telefarmer.ui.view.fragments.previousConsultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmer.primary.network.dataSource.local.LocalData
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentPreviousConsultationBinding
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.PeekingLinearLayoutManager
import com.theroyalsoft.telefarmer.ui.adapters.previousConsultation.PreviousConsultationAdapter
import com.theroyalsoft.telefarmer.ui.view.fragments.home.HomeViewModel
import com.theroyalsoft.telefarmer.utils.isInvisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PreviousConsultationFragment : Fragment() {

    private lateinit var binding: FragmentPreviousConsultationBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var mPreviousConsultationAdapter: PreviousConsultationAdapter

    private var imgUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviousConsultationBinding.inflate(layoutInflater, container, false)

        imgUrl = LocalData.getMetaInfoMetaData()?.imgBaseUrl ?: ""

        initView()

        viewModel.getCallHistory()
        getCallHistory()
        ifApiGetError()

        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.tvToolbarTitle.text = getString(R.string.previous_consultation)

        mPreviousConsultationAdapter = PreviousConsultationAdapter(imgUrl) { prescriptionId ->
            findNavController().navigate(
                PreviousConsultationFragmentDirections.actionPreviousConsultationFragmentToPreviousConsultationDetailsFragment(
                    prescriptionId
                )
            )
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.toolBarLay.apply {
            imgLeft.isInvisible()
            btnBack.setOnClickListener { findNavController().popBackStack() }
        }

        binding.rvPreviousConsultation.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = mPreviousConsultationAdapter
        }
    }

    // Api get
    private fun getCallHistory() {
        lifecycleScope.launch {
            viewModel._historyStateFlow.collect {
                val list = it.callHistory
                mPreviousConsultationAdapter.submitData(list)
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