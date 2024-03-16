package com.theroyalsoft.telefarmer.ui.view.fragments.uploadimg

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentUploadImgBinding
import com.theroyalsoft.telefarmer.extensions.getCameraAndPhotoPermission
import com.theroyalsoft.telefarmer.extensions.resizeBitMapImage1
import com.theroyalsoft.telefarmer.extensions.showLoadingDialog
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.ui.adapters.uploadimg.UploadImageAdapter
import com.theroyalsoft.telefarmer.ui.view.activity.viewimg.ImageLoaderActivity
import com.theroyalsoft.telefarmer.ui.view.fragments.home.HomeViewModel
import com.theroyalsoft.telefarmer.utils.isInvisible
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class UploadImgFragment : Fragment() {

    private lateinit var binding: FragmentUploadImgBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var mUploadImageAdapter: UploadImageAdapter

    private lateinit var bitmapImage: Bitmap

    private var loadingDialog: Dialog? = null

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
            viewModel.uploadFile(imageBody, "labReport")
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadImgBinding.inflate(layoutInflater, container, false)

        loadingDialog = requireContext().showLoadingDialog()

        initView()
        photoPickerInitialize()

        binding.tvUploadImg.setOnClickListener {
            getCameraAndPhotoPermission {
                pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }

        viewModel.getLabReport()
        getResponseLabReport()
        uploadFile()
        ifApiGetError()

        return binding.root
    }

    private fun initView() {
        binding.toolBarLay.apply {
            imgLeft.isInvisible()
            btnBack.setOnClickListener { findNavController().popBackStack() }
            tvToolbarTitle.text = getString(R.string.images)
        }

        mUploadImageAdapter = UploadImageAdapter { imgUrl ->
            //Item Click
            ImageLoaderActivity.goActivityFullPath(requireContext(), imgUrl)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.VERTICAL

        binding.rvUploadImg.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = mUploadImageAdapter
        }
    }

    private fun getResponseLabReport() {
        lifecycleScope.launch {
            viewModel._labStateFlow.collect {
                val list = it.items
                mUploadImageAdapter.submitData(list)
            }
        }
    }

    private fun ifApiGetError() {
        lifecycleScope.launch {
            loadingDialog?.dismiss()
            viewModel._errorFlow.collect { errorStr ->
                withContext(Dispatchers.Main) {
                    if (errorStr.isNotEmpty()) {
                        requireContext().showToast(errorStr)
                    }
                }
            }
        }
    }

    private fun uploadFile() {
        lifecycleScope.launch {
            viewModel._imgUrlStateFlow.collect {
                loadingDialog?.dismiss()
                viewModel.getLabReport()
            }
        }
    }

    //Image
    private fun photoPickerInitialize() {

        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    // Start cropping activity for pre-acquired image saved on the device and customize settings.
                    cropImage.launch(CropImageContractOptions(uri, CropImageOptions(true, true)))
                } else {
                    Log.e("PhotoPicker", "No media selected")
                }
            }
    }

    private fun getFile(context: Context, bm: Bitmap?): File {
        val imgFile = File(context.cacheDir, "image-" + System.currentTimeMillis() + ".jpg")
        try {
            imgFile.createNewFile()
        } catch (e: IOException) {
            Timber.d("Error:$e")
        }
        val bos = ByteArrayOutputStream()
        bm?.compress(Bitmap.CompressFormat.JPEG, 60 /*ignored for PNG*/, bos)
        val bitmapData = bos.toByteArray()
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(imgFile)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Timber.d("Error:$e")
        } catch (e: IOException) {
            Timber.d("Error:$e")
        }
        return imgFile
    }
}