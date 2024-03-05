package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.app.Dialog
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
import androidx.core.view.isGone
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
import com.theroyalsoft.telefarmer.extensions.getFile
import com.theroyalsoft.telefarmer.extensions.resizeBitMapImage1
import com.theroyalsoft.telefarmer.extensions.setImage
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.extensions.showLoadingDialog
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.GridSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.PeekingLinearLayoutManager
import com.theroyalsoft.telefarmer.helper.SpannedGridLayoutManager
import com.theroyalsoft.telefarmer.ui.adapters.previousConsultation.PreviousConsultationAdapter
import com.theroyalsoft.telefarmer.ui.adapters.slider.SliderAdapter
import com.theroyalsoft.telefarmer.ui.adapters.tipsntricks.TipsNTricksHomeAdapter
import com.theroyalsoft.telefarmer.ui.adapters.uploadimg.UploadImageHomeAdapter
import com.theroyalsoft.telefarmer.ui.adapters.weather.WeatherAdapter
import com.theroyalsoft.telefarmer.ui.view.activity.call.CallActivity
import com.theroyalsoft.telefarmer.ui.view.activity.call.CallTestActivity
import com.theroyalsoft.telefarmer.ui.view.activity.chat.ChatActivity
import com.theroyalsoft.telefarmer.ui.view.activity.loan.loanselect.LoanSelectActivity
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
    private lateinit var weatherAdapter: WeatherAdapter
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
                loadingDialog?.show()
                viewModel.uploadFile(imageBody, "labReport")
            }
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    private var imgUrl = "";
    private var loadingDialog: Dialog? = null
    private lateinit var mLayoutManager: PeekingLinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initView()
        event()

        loadingDialog = requireContext().showLoadingDialog()

        imgUrl = LocalData.getMetaInfoMetaData()?.imgBaseUrl ?: ""
        viewModel.getHomeData()

        photoPickerInitialize()

        getCallHistory()
        getLabReport()
        getHomeResponse()
        getWeather()

        uploadFile()
        ifApiGetError()
        return binding.root
    }

    private fun initView() {
        //ifApiGetError()

        binding.llUploadImage.rvUploadImg.addItemDecoration(EqualSpacingItemDecoration(20))

        sliderAdapter = SliderAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToNewsDetailsFragment(it)
            findNavController().navigate(action)
        }

        previousConsultationAdapter = PreviousConsultationAdapter()

//
        rvSetUpPreviousConsultation()
        rvWeatherSet()
//        rvImageUploadSetup()

        mTipsNTricksHomeAdapter = TipsNTricksHomeAdapter {
            val action =
                HomeFragmentDirections.actionHomeFragmentToTipsNTricksListFragment(
                    it.uuid ?: "-1",
                    it.nameBn ?: ""
                )
            findNavController().navigate(action)
        }
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

    private fun rvSlider() {
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
        mLayoutManager = PeekingLinearLayoutManager(context)
//        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.setRation(0.98F)
        mLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.llPreviousConsultation.rvPreviousConsultationHome.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            addItemDecoration(EqualSpacingItemDecoration(10))
            adapter = previousConsultationAdapter
        }
    }

    private fun rvImageUploadSetup(list: List<ResponseLabReport.ItemLabReport>) {
        mUploadImageHomeAdapter = UploadImageHomeAdapter(imgUrl)
        var data: List<Int>
        if (list.size > 2) {
            data = listOf(0, 1, 2)
        } else if (list.size > 1) {
            data = listOf(0, 1)
        } else {
            data = listOf(0)
        }

        var gridLayoutManager: LayoutManager
        if (list.size > 1) {
            gridLayoutManager = SpannedGridLayoutManager({ position ->
                val spanInfo = SpannedGridLayoutManager.SpanInfo(1, 1)
                if (data[position] == 1) {
                    spanInfo.columnSpan = 1
                    spanInfo.rowSpan = 1
                } else if (data[position] == 0) {
//                    spanInfo.columnSpan = 1
                    spanInfo.rowSpan = 1
                } else if (data[position] == 2) {
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
            adapter = mUploadImageHomeAdapter
        }
        mUploadImageHomeAdapter.submitData(list)
    }

    private fun rvWeatherSet() {
        weatherAdapter = WeatherAdapter { }
        val mLayoutManager = PeekingLinearLayoutManager(context)
        mLayoutManager.setRation(0.20f)
//        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.llWeather.rvWeather.apply {
            layoutManager = mLayoutManager
//            addItemDecoration(EqualSpacingItemDecoration(10))
            adapter = weatherAdapter
        }
    }

    private fun event() {
        binding.apply {
            imgAudioCall.setOnClickListener {
                getCameraAndMicPermission {
                    val intent = Intent(requireActivity(), CallActivityKotlin::class.java)
                    intent.putExtra(AppKey.INTENT_VIDEO_CALL, false);
                    startActivity(intent);
                }
            }

            imgVideoCall.setOnClickListener {
                //check if permission of mic and camera is taken
                getCameraAndMicPermission {
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

            llLoan.root.setOnClickListener {
                startActivity(
                    LoanSelectActivity.newIntent(
                        requireContext(),
                        phone = ""
                    )
                )
            }

            llChat.root.setSafeOnClickListener {
                getCameraAndPhotoPermission {
                    startActivity(
                        ChatActivity.newIntent(
                            requireContext(),
                            phone = ""
                        )
                    )
                }
            }

            llSlider.btnViewAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToNewsListFragment()
                findNavController().navigate(action)
            }

            llTipsNTricks.btnViewAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToTipsNTricksListFragment()
                findNavController().navigate(action)
            }


            llUploadImage.btnViewAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToUploadImgFragment()
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
                withContext(Dispatchers.Main) {
                    mLayoutManager.setRation(0.7F)
                    binding.llPreviousConsultation.rvPreviousConsultationHome.layoutManager = mLayoutManager
                    previousConsultationAdapter.submitData(list)
                }
            }
        }
    }

    private fun getLabReport() {
        lifecycleScope.launch {
            viewModel._labStateFlow.collect {
                val list = it.items.take(3)
                withContext(Dispatchers.Main) {
                    rvImageUploadSetup(list)
                }
            }
        }
    }

    private fun uploadFile() {
        lifecycleScope.launch {
            viewModel._imgUrlStateFlow.collect {
                withContext(Dispatchers.Main) {
                    loadingDialog?.dismiss()
                    viewModel.getLabReport()
                }
            }
        }
    }

    private fun getHomeResponse() {
        lifecycleScope.launch {
            viewModel._homeStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    data?.News?.let {
                        sliderAdapter.submitData(it.take(4))
                    }
                    rvSlider()
                    data?.TipsCategories?.let {
                        mTipsNTricksHomeAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun getWeather() {
        lifecycleScope.launch {
            viewModel._weatherRes.collect { data ->
                withContext(Dispatchers.Main) {
                    weatherAdapter.submitData(data.forecast.forecastday)
                    binding.apply {
                        val img = "http:${data.current.condition.icon}"
                        imgWeather.setImage(img)
                        tvWeatherC.text = "${data.current.temp_c} °c"
                        tvWeatherStatus.text = data.current.condition.text
                    }

                    binding.llWeather.apply {
                        val img = "http:${data.current.condition.icon}"
                        imgWeather.setImage(img)
                        tvWeatherC.text = "${data.current.temp_c}°"
                        tvWeatherStatus.text = data.current.condition.text
                    }
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


}
