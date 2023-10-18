package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bio.medico.patient.callingWebrtc.CallActivityKotlin
import bio.medico.patient.common.AppKey
import com.theroyalsoft.telefarmer.databinding.FragmentHomeBinding
import com.theroyalsoft.telefarmer.extensions.getCameraAndMicPermission
import com.theroyalsoft.telefarmer.ui.view.activity.call.CallTestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

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
    }

    private fun event() {
        binding.apply {
            imgAudioCall.setOnClickListener {
                getCameraAndMicPermission {
//                    val intent = Intent(activity, CallTestActivity::class.java)
//                    intent.putExtra("isVideoCall", false)
//                    startActivity(intent)
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
