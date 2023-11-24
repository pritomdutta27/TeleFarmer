package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.bottomlist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentListBottomSheetBinding
import com.theroyalsoft.telefarmer.extensions.hideKeyboard
import com.theroyalsoft.telefarmer.extensions.setItemDecorationSpacingDivider
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.custom.ListBottomSheetCallBack
import com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.bottomlist.adapter.ListBottomSheetAdapter
import timber.log.Timber

/**
 * Created by Pritom Dutta on 24/11/23.
 */
class ListBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentListBottomSheetBinding? = null
    private val binding get() = _binding!!

    var callback: ListBottomSheetCallBack<LoanDetailsResponseItem>? = null

    private lateinit var mListBottomSheetAdapter: ListBottomSheetAdapter

    val list = mutableListOf<String>()

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
        mListBottomSheetAdapter = ListBottomSheetAdapter()

        _binding?.apply {
            rv.setItemDecorationSpacingDivider(45F)
            rv.adapter = mListBottomSheetAdapter
        }
    }

    private fun event() {
        mListBottomSheetAdapter.onClickItem { data ->
            Log.e("LoanDetailsResponseItem", "bind: "+data.crop_name )
            callback?.invoke(data)
            activity?.hideKeyboard(binding.rv)
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
        list: List<LoanDetailsResponseItem>,
        title: String,
        callback: ListBottomSheetCallBack<LoanDetailsResponseItem>
    ) {
        _binding?.tvTitle?.text = title
        this.callback = callback
        Timber.e("ListBottomSheet: " + list.toString())
        if (this::mListBottomSheetAdapter.isInitialized){
            initView()
            event()
        }
        mListBottomSheetAdapter.modifyList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}