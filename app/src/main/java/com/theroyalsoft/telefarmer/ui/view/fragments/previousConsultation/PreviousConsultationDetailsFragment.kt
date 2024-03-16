package com.theroyalsoft.telefarmer.ui.view.fragments.previousConsultation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bio.medico.patient.common.AppKey
import com.farmer.primary.network.dataSource.local.LocalData
import com.skh.hkhr.util.log.ToastUtil
import com.skh.hkhr.util.view.LoadingUtil
import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.FragmentPreviousConsultationDetailsBinding
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.utils.isInvisible
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match

class PreviousConsultationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPreviousConsultationDetailsBinding
    private val args: PreviousConsultationDetailsFragmentArgs by navArgs()

    private var webSettings: WebSettings? = null
    private var url = ""
    var wvPrescriptionPreview: WebView? = null
    var prescriptionId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (arguments != null) {
            prescriptionId = args.prescriptionId
        }
        binding =
            FragmentPreviousConsultationDetailsBinding.inflate(layoutInflater, container, false)

        wvPrescriptionPreview = binding.wvPrescriptionPreview
        initUI()
        return binding.root
    }

    private fun initUI() {
       // setLanguage()
        binding.toolBarLay.tvToolbarTitle.text = getString(R.string.previous_consultation_details)
        binding.toolBarLay.apply {
            imgLeft.isInvisible()
            btnBack.setOnClickListener { findNavController().popBackStack() }
        }

        if (!NetworkUtils.isConnected()) {
            ToastUtil.showToastMessage(AppKey.ERROR_INTERNET_CONNECTION)
        }
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(wvPrescriptionPreview, true)
        } else {
            CookieManager.getInstance().setAcceptCookie(true)
        }
        try {

            webSettings = wvPrescriptionPreview?.getSettings()
            webSettings?.javaScriptEnabled = true
            webSettings?.javaScriptCanOpenWindowsAutomatically = true
            wvPrescriptionPreview?.getSettings()?.setLoadsImagesAutomatically(true)
            webSettings?.builtInZoomControls = true
            webSettings?.displayZoomControls = false
        } catch (e: NullPointerException) {
            Timber.e("Error:$e")
        }
        binding.wvPrescriptionPreview.setWebViewClient(WebViewClient())
        binding.wvPrescriptionPreview.setDownloadListener(DownloadListener { downloadUrl, userAgent, contentDisposition, mimetype, contentLength ->
            try {
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(url))
                startActivity(i)
            } catch (e: Exception) {
                Timber.e("download error::: $e")
            }
        })
        binding.wvPrescriptionPreview.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    LoadingUtil.hide()
                }
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedIcon(view: WebView, icon: Bitmap) {
                super.onReceivedIcon(view, icon)
            }
        })
        binding.wvPrescriptionPreview.setBackgroundColor(Color.GRAY)
        binding.wvPrescriptionPreview.setWebViewClient(object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                //Toast.makeText(activity, "Please turn on data connection!", Toast.LENGTH_SHORT).show();
                binding.wvPrescriptionPreview.setVisibility(View.GONE)
                binding.tvStatus.setVisibility(View.VISIBLE)
                binding.btnRefresh.setVisibility(View.VISIBLE)
                LoadingUtil.hide()
            }

//            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
//                Log.e("onPageStarted:: ", url)
//                //LoadingUtil2.show(AppKey.FETCHING_PRESCRIPTION, activity)
//            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false //Allow WebView to load url
            }
        })

        url = LocalData.getMetaInfoMetaData()
            .doctorBaseUrl + "iframe-prescription/" + prescriptionId
//        url = "https://doctor.telefarmer.net/iframe-advices/e3593d7c-c118-408e-945f-2b5e096ddbc7"
        binding.wvPrescriptionPreview.loadUrl(url)
        Timber.e("externalUrl: $url")

        binding.btnRefresh.setOnClickListener(View.OnClickListener { v: View? ->
            binding.wvPrescriptionPreview.setVisibility(View.VISIBLE)
            binding.tvStatus.setVisibility(View.GONE)
            binding.btnRefresh.setVisibility(View.GONE)
            binding.wvPrescriptionPreview.loadUrl(url)
        })
//        binding.tvSelectForOrder.setOnClickListener(object : OnSingleClickListener() {
//            override fun onSingleClick(view: View) {
//                MedicineOrderManager.selectPrescription = url
//                finish()
//            }
//        })
        binding.toolBarLay.btnBack.setSafeOnClickListener { findNavController().popBackStack() }
    }

}