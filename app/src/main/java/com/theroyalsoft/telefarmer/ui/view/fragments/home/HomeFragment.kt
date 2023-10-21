package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import bio.medico.patient.callingWebrtc.CallActivityKotlin
import bio.medico.patient.common.AppKey
import bio.medico.patient.model.apiResponse.ResponseLabReport
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.farmer.primary.network.dataSource.local.LocalData
import com.theroyalsoft.telefarmer.databinding.FragmentHomeBinding
import com.theroyalsoft.telefarmer.extensions.autoScroll
import com.theroyalsoft.telefarmer.extensions.getCameraAndMicPermission
import com.theroyalsoft.telefarmer.extensions.getCameraAndPhotoPermission
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.GridSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.PeekingLinearLayoutManager
import com.theroyalsoft.telefarmer.helper.SpannedGridLayoutManager
import com.theroyalsoft.telefarmer.ui.adapters.previousConsultation.PreviousConsultationAdapter
import com.theroyalsoft.telefarmer.ui.adapters.slider.SliderAdapter
import com.theroyalsoft.telefarmer.ui.adapters.tipsntricks.TipsNTricksHomeAdapter
import com.theroyalsoft.telefarmer.ui.adapters.uploadimg.UploadImageHomeAdapter
import com.theroyalsoft.telefarmer.utils.ImagePickUpUtil
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
import java.util.Timer

@AndroidEntryPoint
class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var previousConsultationAdapter: PreviousConsultationAdapter
    private lateinit var mUploadImageHomeAdapter: UploadImageHomeAdapter
    private lateinit var mTipsNTricksHomeAdapter: TipsNTricksHomeAdapter

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

            /* try {
                 val compressToBitmap: Bitmap =
                     Compressor(requireContext()).compressToBitmap(imgFile)
                 imgFile = getFile(requireContext(), compressToBitmap)
             } catch (e: IOException) {
                 Timber.e("Error:$e")
                 return@registerForActivityResult
             }*/

            val imgFileName: String = imgFile.name

            val mediaType: MediaType? = "image/jpg".toMediaTypeOrNull()

            val requestFile: RequestBody = imgFile.asRequestBody(mediaType)
            val imageBody: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                imgFileName,
                requestFile
            )
            viewModel.uploadFile(imageBody)
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    private var imgUrl = "";

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initView()
        event()

        imgUrl = LocalData.getMetaInfoMetaData().imgBaseUrl
        viewModel.getHomeData()

        photoPickerInitialize()

        getCallHistory()
        getLabReport()
        getHomeResponse()
        ifApiGetError()
        return binding.root
    }

    private fun initView() {
        //ifApiGetError()

        binding.llUploadImage.rvUploadImg.addItemDecoration(EqualSpacingItemDecoration(20))

        sliderAdapter = SliderAdapter()

        previousConsultationAdapter = PreviousConsultationAdapter()

//
        rvSetUpPreviousConsultation()
//        rvImageUploadSetup()

        mTipsNTricksHomeAdapter = TipsNTricksHomeAdapter()
        val spanCount = 4
        val spacing = 40
        val includeEdge = true
        val gridLayout = GridLayoutManager(requireContext(), spanCount)

        binding.llTipsNTricks.rvTipsNTricks.apply {
            layoutManager = gridLayout
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    spacing,
                    includeEdge
                )
            )
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            adapter = mTipsNTricksHomeAdapter
        }
    }

    private fun rvSlider(){
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
    }

    private fun rvSetUpPreviousConsultation() {
        val mLayoutManager = PeekingLinearLayoutManager(context)
//        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.llPreviousConsultation.rvPreviousConsultationHome.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(40))
            adapter = previousConsultationAdapter
        }
    }

    private fun rvImageUploadSetup(list: List<ResponseLabReport.ItemLabReport>) {
        mUploadImageHomeAdapter = UploadImageHomeAdapter(imgUrl)
        //val data = list
        var gridLayoutManager: LayoutManager
        if (list.size > 1) {
            gridLayoutManager = SpannedGridLayoutManager({ position ->
                val spanInfo = SpannedGridLayoutManager.SpanInfo(1, 1)
                if (position == 1) {
                    spanInfo.columnSpan = 1
                    spanInfo.rowSpan = 1
                } else if (position == 0) {
                    spanInfo.columnSpan = 1
//                spanInfo.rowSpan = 1
                } else if (position == 3) {
                    spanInfo.columnSpan = 2
                    spanInfo.rowSpan = 1
                }
                spanInfo
            }, 2, 1.5f)
        } else {
            gridLayoutManager = LinearLayoutManager(context)
            gridLayoutManager.orientation = RecyclerView.HORIZONTAL
        }


//        val mLayoutManagerT =
        binding.llUploadImage.rvUploadImg.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)

            adapter = mUploadImageHomeAdapter
        }
        mUploadImageHomeAdapter.submitData(list)
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

            llPreviousConsultation.btnViewAll.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToPreviousConsultationFragment()
                findNavController().navigate(action)
            }

            llUploadImage.btnViewAll.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToUploadImgFragment()
                findNavController().navigate(action)
            }

            llUploadImage.btnUploadImage.setOnClickListener {
                getCameraAndPhotoPermission {
                    pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
        }
    }


    // Api get
    private fun getCallHistory() {
        lifecycleScope.launch {
            viewModel._historyStateFlow.collect {
                val list = it.callHistory.take(5)
                previousConsultationAdapter.submitData(list)
            }
        }
    }

    private fun getLabReport() {
        lifecycleScope.launch {
            viewModel._labStateFlow.collect {
                val list = it.items.take(3)
                rvImageUploadSetup(list)
            }
        }
    }

    private fun getHomeResponse() {
        lifecycleScope.launch {
            viewModel._homeStateFlow.collect {
                val list = it.static
                sliderAdapter.submitData(list.news.take(4))
                rvSlider()
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

    private fun getFile(context: Context, bm: Bitmap): File {
        val imgFile = File(context.cacheDir, "image-" + System.currentTimeMillis() + ".jpg")
        try {
            imgFile.createNewFile()
        } catch (e: IOException) {
            Timber.d("Error:$e")
        }
        val bos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
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


//
