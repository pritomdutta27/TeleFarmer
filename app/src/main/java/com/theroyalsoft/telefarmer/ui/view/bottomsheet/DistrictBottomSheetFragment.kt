package com.theroyalsoft.telefarmer.ui.view.bottomsheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentListBottomSheetBinding
import com.theroyalsoft.telefarmer.extensions.hideKeyboard
import com.theroyalsoft.telefarmer.extensions.setItemDecorationSpacingDivider
import com.theroyalsoft.telefarmer.ui.custom.ListBottomSheetCallBack
import live.tech.view.ListBottomSheetWithSearch.adapter.DistrictBottomSheetAdapter
import timber.log.Timber


class DistrictBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentListBottomSheetBinding? = null
    private val binding get() = _binding!!

    var callback: ListBottomSheetCallBack<DivisionDataModel>? = null

    private lateinit var mListBottomSheetAdapter: DistrictBottomSheetAdapter

    val list = mutableListOf<DivisionDataModel>()

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBottomSheetBinding.inflate(inflater, container, false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initView()
        event()
        return binding.root
    }

    private fun initView() {
        mListBottomSheetAdapter = DistrictBottomSheetAdapter()

        _binding?.apply {
            rv.setItemDecorationSpacingDivider(45F)
            rv.adapter = mListBottomSheetAdapter
        }
    }

    private fun event() {
        mListBottomSheetAdapter.onClickItem { data ->
            callback?.invoke(data)
            activity?.hideKeyboard(binding.etSearch)
            binding.etSearch.text.clear()
            dismiss()
        }

//        binding.etSearch.addTextChangedListener(object :
//            TextChangedListener<EditText?>(binding.etSearch) {
//            override fun onTextChanged(target: EditText?, s: Editable?) {
//                mListBottomSheetAdapter.filter(s.toString())
//            }
//        })
    }

    fun submitData(
        list: List<DivisionDataModel>,
        title: String,
        callback: ListBottomSheetCallBack<DivisionDataModel>
    ) {
        _binding?.tvTitle?.text = title
        this.callback = callback
        Timber.e("ListBottomSheet: " + list.toString())
        mListBottomSheetAdapter.modifyList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}