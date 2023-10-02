package com.theroyalsoft.telefarmer.ui.view.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.theroyalsoft.telefarmer.extensions.getPhoneDeviceId
import com.theroyalsoft.telefarmer.extensions.openLogout
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentProfileBinding
import com.theroyalsoft.telefarmer.ui.view.activity.login.LoginActivity
import com.theroyalsoft.telefarmer.utils.isInvisible
import dagger.hilt.android.AndroidEntryPoint
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var pref: DataStoreRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        initUI()
        event()

        //Api Call
        getResponse()
        ifApiGetError()

        return binding.root
    }

    private fun initUI() {
        binding.apply {
            toolBarLay.btnBack.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.profile)
        }
    }

    private fun event() {
        binding.apply {
            toolBarLay.imgLeft.setSafeOnClickListener {
                requireContext().openLogout {
                    viewModel.logout(requireContext().getPhoneDeviceId())
                }
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            pref.userLoginOut()
            openLogin()
        }
    }

    private fun openLogin() {
        startActivity(LoginActivity.newIntent(requireContext()))
        requireActivity().finish()
    }

    //TODO: API
    private fun getResponse() {
        lifecycleScope.launch {
            viewModel._logoutStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    if (data.isSuccess) {
                        logout()
                    } else {
                        requireContext().showToast(data.message)
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