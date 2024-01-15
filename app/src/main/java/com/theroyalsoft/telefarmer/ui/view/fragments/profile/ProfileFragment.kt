package com.theroyalsoft.telefarmer.ui.view.fragments.profile

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.farmer.primary.network.dataSource.local.LocalData
import com.google.gson.Gson
import com.theroyalsoft.telefarmer.extensions.getPhoneDeviceId
import com.theroyalsoft.telefarmer.extensions.openLogout
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentProfileBinding
import com.theroyalsoft.telefarmer.extensions.getCameraAndPhotoPermission
import com.theroyalsoft.telefarmer.extensions.getFile
import com.theroyalsoft.telefarmer.extensions.getFromDateTime
import com.theroyalsoft.telefarmer.extensions.resizeBitMapImage1
import com.theroyalsoft.telefarmer.extensions.setImage
import com.theroyalsoft.telefarmer.extensions.setImageProfile
import com.theroyalsoft.telefarmer.ui.custom.DatePickerFragment
import com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.bottomlist.ListBottomSheetFragment
import com.theroyalsoft.telefarmer.ui.view.activity.login.LoginActivity
import com.theroyalsoft.telefarmer.ui.view.bottomsheet.DistrictBottomSheetFragment
import com.theroyalsoft.telefarmer.ui.view.bottomsheet.DivisionDataModel
import com.theroyalsoft.telefarmer.utils.isInvisible
import dagger.hilt.android.AndroidEntryPoint
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
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
import java.lang.Exception
import javax.inject.Inject

data class Location(val district: String, val thana: String)

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var datePickerDialog: DatePickerFragment

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var mDistrictBottomSheetFragment: DistrictBottomSheetFragment

    @Inject
    lateinit var pref: DataStoreRepository

    var listDistricts = emptyList<DivisionDataModel>()

    /////*For image */
    private lateinit var bitmapImage: Bitmap
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            //val uriFilePath = result.getUriFilePath(requireContext()) // optional usage

            bitmapImage =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uriContent)
            //uploadImage(bitmapImage)

            bitmapImage.let { image ->
                var imgFile: File = requireContext().getFile(image)
                try {
                    val compressToBitmap = imgFile.path.resizeBitMapImage1(200, 200)
                    imgFile = requireContext().getFile(compressToBitmap)
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
//                loadingDialog?.show()
//                viewModel.uploadFile(imageBody, "labReport")
            }
        } else {
            // An error occurred.
            val exception = result.error
        }
    }
    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var imgUrl = "";

    //////////////////////////////////////////
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        initUI()
        photoPickerInitialize()
        event()

        listDistricts = viewModel.getDistricts()

        //Api Call
        getResponse()
        ifApiGetError()

        setProfileData()


        return binding.root
    }

    private fun setProfileData() {
        val data = LocalData.getResponsePatientInfo()
        binding.apply {
            etName.setText(data.name)
            etPhone.setText(data.phoneNumber)
            imgProfile.setImageProfile(LocalData.getImgBaseUrl() + data.image)
            etExpiry.text = data.dob
            try {
                val gson = Gson()
                val locationModel = gson.fromJson(data.location, Location::class.java)
                etLocation.text = locationModel.district
            } catch (e: Exception) {
                println(e.message)
            }

        }
    }

    private fun initUI() {
        datePickerDialog = DatePickerFragment()

        mDistrictBottomSheetFragment = DistrictBottomSheetFragment()

        binding.apply {
            toolBarLay.btnBack.isInvisible()
            toolBarLay.tvToolbarTitle.text = getString(R.string.profile)
        }
    }

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

    private fun event() {

        binding.apply {

            imgProfile.setSafeOnClickListener {
                getCameraAndPhotoPermission {
                    pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }

            toolBarLay.imgLeft.setSafeOnClickListener {
                requireContext().openLogout {
                    viewModel.logout(requireContext().getPhoneDeviceId())
                }
            }

            llExpiry.setOnClickListener {
                clearFocus()
                if (!datePickerDialog.isAdded) {
                    datePickerDialog.showNow(childFragmentManager, "DatePicker")
                }
            }

            datePickerDialog.setOnClick {
                clearFocus()
                binding.etExpiry.text = it.getFromDateTime("yyyy/MM/dd", "dd MMMM yyyy")
            }

            etLocation.setSafeOnClickListener {
                if (!mDistrictBottomSheetFragment.isAdded) {
                    mDistrictBottomSheetFragment.showNow(childFragmentManager, "ListBottomFragment")
                }
                mDistrictBottomSheetFragment.submitData(listDistricts, "জেলার নাম") { data ->
                    etLocation.text = data.bn_name
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

    private fun clearFocus() {
        binding.apply {
            etName.clearFocus()
            etPhone.clearFocus()
        }
    }

}