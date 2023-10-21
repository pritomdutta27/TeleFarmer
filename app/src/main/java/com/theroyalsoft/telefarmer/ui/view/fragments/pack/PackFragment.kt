package com.theroyalsoft.telefarmer.ui.view.fragments.pack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentPackBinding
import com.theroyalsoft.telefarmer.helper.GridSpacingItemDecoration
import com.theroyalsoft.telefarmer.ui.adapters.pack.PackAdapter
import com.theroyalsoft.telefarmer.utils.isInvisible
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [PackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PackFragment : Fragment() {

    private lateinit var binding: FragmentPackBinding
    private lateinit var packAdapter: PackAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPackBinding.inflate(layoutInflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        packAdapter = PackAdapter()
        binding.apply {
            toolBarLay.btnBack.isInvisible()
            toolBarLay.imgLeft.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.buy_pack)
        }
        val spanCount = 2
        val spacing = 40
        val includeEdge = true
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)

        binding.rvPackList.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    spacing,
                    includeEdge
                )
            )
            adapter = packAdapter
        }
    }


}