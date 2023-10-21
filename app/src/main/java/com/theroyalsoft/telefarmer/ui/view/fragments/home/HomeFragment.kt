package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import bio.medico.patient.callingWebrtc.CallActivityKotlin
import bio.medico.patient.common.AppKey
import com.theroyalsoft.telefarmer.databinding.FragmentHomeBinding
import com.theroyalsoft.telefarmer.extensions.autoScroll
import com.theroyalsoft.telefarmer.extensions.getCameraAndMicPermission
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.SpannedGridLayoutManager
import com.theroyalsoft.telefarmer.ui.adapters.previousConsultation.PreviousConsultationAdapter
import com.theroyalsoft.telefarmer.ui.adapters.slider.SliderAdapter
import com.theroyalsoft.telefarmer.ui.adapters.uploadimg.UploadImageHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var previousConsultationAdapter: PreviousConsultationAdapter
    private lateinit var mUploadImageHomeAdapter: UploadImageHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initView()
        event()

        // viewModel.fetchMetaData()

        return binding.root
    }

    private fun initView() {
        //ifApiGetError()

        sliderAdapter = SliderAdapter()
        binding.apply {
            llSlider.apply {
                viewPagerSlider.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                viewPagerSlider.adapter = sliderAdapter

                viewPagerSlider.clipToPadding = false
                viewPagerSlider.clipChildren = false
                viewPagerSlider.offscreenPageLimit = 1
                viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(30))
//                compositePageTransformer.addTransformer { page, position ->
//                    val r = 1 - Math.abs(position)
//                    page.scaleY = 0.75f + r * 0.25f
//                }
                viewPagerSlider.setPageTransformer(compositePageTransformer)
                dotsIndicator.apply {
                    setPageSize(viewPagerSlider.adapter!!.itemCount)
                    setupWithViewPager(viewPagerSlider)
                }
                val handler = Handler(Looper.getMainLooper())
                val mTimer = Timer()
                viewPagerSlider.autoScroll(
                    4, 0,
                    indicator = dotsIndicator, handler, mTimer
                )
            }
        }


        previousConsultationAdapter = PreviousConsultationAdapter()

//        val mLayoutManager = PeekingLinearLayoutManager(context)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.llPreviousConsultation.rvPreviousConsultationHome.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = previousConsultationAdapter
        }

        mUploadImageHomeAdapter = UploadImageHomeAdapter()
        val data = listOf(1,2,3)
        val gridLayoutManager = SpannedGridLayoutManager({ position ->
            val spanInfo = SpannedGridLayoutManager.SpanInfo(1, 1)
            if (data[position] == 1) {
                spanInfo.columnSpan = 1
                spanInfo.rowSpan = 1
            } else if (data[position] == 0) {
//                spanInfo.columnSpan = 1
                spanInfo.rowSpan = 1
            } else if (data[position] == 3) {
                spanInfo.columnSpan = 2
                spanInfo.rowSpan = 1
            }
            spanInfo
        }, 2, 1.5f)

//        val mLayoutManagerT = LinearLayoutManager(context)
//        mLayoutManagerT.orientation = RecyclerView.HORIZONTAL
        binding.llUploadImage.rvUploadImg.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(10))
            adapter = mUploadImageHomeAdapter
        }
    }

    private fun event() {
        binding.apply {
            imgAudioCall.setOnClickListener {
                getCameraAndMicPermission {
//                    val intent = Intent(activity, CallTestActivity::class.java)
//                    intent.putExtra("isVideoCall", false)
//                    startActivity(intent)
                    val intent = Intent(requireActivity(), CallActivityKotlin::class.java)
                    intent.putExtra(AppKey.INTENT_VIDEO_CALL, false);
                    startActivity(intent);
                }
            }

            imgVideoCall.setOnClickListener {
//                Timber.e("isVideoCall:")
//                val intent = Intent(requireActivity(), CallTestActivity::class.java)
//                startActivity(intent)
                //check if permission of mic and camera is taken
                getCameraAndMicPermission {
//                    val intent = Intent(requireActivity(), CallTestActivity::class.java)
//                    intent.putExtra("isVideoCall", true)
//                    startActivity(intent)
                    val intent = Intent(requireActivity(), CallActivityKotlin::class.java)
                    intent.putExtra(AppKey.INTENT_VIDEO_CALL, true);
                    startActivity(intent);
                }
            }
        }
    }
}

//    private fun ifApiGetError() {
//        lifecycleScope.launch {
//            viewModel._errorFlow.collect { errorStr ->
//                withContext(Dispatchers.Main) {
//                    if (errorStr.isNotEmpty()) {
//                        requireContext().showToast(errorStr)
//                    }
//                }
//            }
//        }
//    }
