package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentLoadDetailsBottomSheetBinding
import com.theroyalsoft.telefarmer.extensions.getCameraAndPhotoPermission
import com.theroyalsoft.telefarmer.extensions.resizeBitMapImage1
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.extensions.showLoadingDialog
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet.adapter.LoanDetailsAdapter
import com.theroyalsoft.telefarmer.ui.view.activity.loan.loansuccess.LoanSuccessActivity
import com.theroyalsoft.telefarmer.utils.ImagePickUpUtil.getFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class LoadDetailsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLoadDetailsBottomSheetBinding
    private val viewModel: LoadDetailsBottomViewModel by viewModels()
    private lateinit var mLoanDetailsAdapter: LoanDetailsAdapter

    private var mLoanDetailsResponseItem: LoanDetailsResponseItem? = null
    private var amountOfLand: Float? = 0.0f
    private var totalAmount: Float = 0.0f

    //for check front on back
    private var isFront = false

    private lateinit var bitmapImage: Bitmap
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            //val uriFilePath = result.getUriFilePath(requireContext()) // optional usage

            bitmapImage =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uriContent)
            //uploadImage(bitmapImage)

            var imgFile: File = getFile(requireContext(), bitmapImage!!)

            try {
                val compressToBitmap = imgFile.path.resizeBitMapImage1(200, 200)
                imgFile = getFile(requireContext(), compressToBitmap)
            } catch (e: IOException) {
                Timber.e("Error:$e")
                return@registerForActivityResult
            }

            val imgFileName: String = imgFile.name

            val mediaType: MediaType? = "image/jpg".toMediaTypeOrNull()

            val requestFile: RequestBody = imgFile.asRequestBody(mediaType)
            val imageBody: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                imgFileName,
                requestFile
            )
            loadingDialog?.show()
            viewModel.uploadFile(
                imageBody,
                "nidCard",
                isFront,
                loadingDialog,
                binding.txtFront,
                binding.tvBack
            )
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    private var loadingDialog: Dialog? = null
    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        binding = FragmentLoadDetailsBottomSheetBinding.inflate(layoutInflater, container, false)

        loadingDialog = requireContext().showLoadingDialog()

        initView()
        event()
        getResponse()
        ifApiGetError()

        registerTakePictureLauncher(initTempUri())
        return binding.root
    }

    private fun initView() {
        mLoanDetailsAdapter = LoanDetailsAdapter()

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvLoadDetailsList.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(20))
            adapter = mLoanDetailsAdapter
        }
    }

    private fun event() {
        binding.apply {

            btnCancel.setOnClickListener { dismiss() }

            btnSubmit.setSafeOnClickListener {
                if (viewModel.applyLoan(
                        cropName = mLoanDetailsResponseItem?.crop_name ?: "",
                        landAmount = amountOfLand.toString(),
                        totalLoan = totalAmount.toString()
                    )
                ) {
                    requireContext().showToast(getString(R.string.nid_back_front))
                }
                loadingDialog?.show()
            }
        }
    }

    fun setData(data: LoanDetailsResponseItem?, amountOfLand: Float) {
        mLoanDetailsResponseItem = data
        this.amountOfLand = amountOfLand
        totalAmount = 0f
        mLoanDetailsResponseItem?.crop_cost?.forEach {
            it.price = (it.price.toFloat() * amountOfLand).toString()
            totalAmount += it.price.toFloat()
        }

        initView()
        event()
        binding.tvTotalLoanAmount.text = "${totalAmount} টাকা"

        mLoanDetailsResponseItem?.crop_cost?.let {
            mLoanDetailsAdapter.submitData(it)
        }
    }

    //For Image
    private fun initTempUri(): Uri {
        //gets the temp_images dir
        val tempImagesDir = File(
            requireContext().filesDir, //this function gets the external cache dir
            getString(R.string.temp_images_dir)
        ) //gets the directory for the temporary images dir

        tempImagesDir.mkdir() //Create the temp_images dir

        //Creates the temp_image.jpg file
        val tempImage = File(
            tempImagesDir, //prefix the new abstract path with the temporary images dir path
            getString(R.string.temp_image)
        ) //gets the abstract temp_image file name

        //Returns the Uri object to be used with ActivityResultLauncher
        return FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.authorities),
            tempImage
        )
    }

    private fun registerTakePictureLauncher(path: Uri) {

        //Creates the ActivityResultLauncher
        val pickMediaT = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                //imageView.setImageURI(null) //rough handling of image changes. Real code need to handle different API levels.
                cropImage.launch(CropImageContractOptions(path, CropImageOptions(true, true)))
            }
        }

        binding.apply {
            clBtnNidFront.setSafeOnClickListener {
                getCameraAndPhotoPermission {
                    pickMediaT.launch(path)
                    isFront = true
                }
            }

            clBtnNidBack.setSafeOnClickListener {
                getCameraAndPhotoPermission {
                    pickMediaT.launch(path)
                    isFront = false
                }
            }
        }
    }

    // Api Call
    private fun getResponse() {
        lifecycleScope.launch {
            viewModel._successFlow.collect { flag ->
                loadingDialog?.dismiss()
                if (flag){
                    startActivity(LoanSuccessActivity.newIntent(requireContext()))
                    dismiss()
                    requireActivity().finish()
                }
            }
        }
    }
    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                loadingDialog?.dismiss()
                withContext(Dispatchers.Main) {
                    if (errorStr.isNotEmpty()) {
                        requireContext().showToast(errorStr)
                    }
                }
            }
        }
    }
}