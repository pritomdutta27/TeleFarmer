package com.theroyalsoft.telefarmer.ui.view.activity.viewimg

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bio.medico.patient.common.AppKey
import bio.medico.patient.common.AppKeyLog
import com.farmer.primary.network.dataSource.local.LocalData
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.github.piasy.biv.loader.fresco.FrescoImageLoader
import com.github.piasy.biv.view.FrescoImageViewFactory
import com.skh.hkhr.util.IntentUtil
import com.theroyalsoft.mydoc.apputil.baseUI.FullScreenActivity
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityImageLoaderBinding

class ImageLoaderActivity : FullScreenActivity() {

    private val uiName = AppKeyLog.UI_IMAGE_LOADER
    private lateinit var binding: ActivityImageLoaderBinding

    companion object {
        fun goActivityFullPath(context: Context, imageUrl: String) {
            val intent = Intent(context, ImageLoaderActivity::class.java)
            intent.putExtra(
                AppKey.INTENT_KEY_NAME, LocalData.getMetaInfoMetaData().imgBaseUrl + "/uploaded/" + imageUrl
            )
            context.startActivity(intent)
        }

        fun goActivity(context: Context, imageUrl: String) {
            val intent = Intent(context, ImageLoaderActivity::class.java)
            intent.putExtra(
                AppKey.INTENT_KEY_NAME,
                LocalData.getMetaInfoMetaData().imgBaseUrl + "/uploaded/" + imageUrl
            )
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BigImageViewer.initialize(FrescoImageLoader.with(applicationContext))

        binding = ActivityImageLoaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = IntentUtil.getIntentValue(AppKey.INTENT_KEY_NAME, intent)

        binding.apply {
            mBigImage.apply {
                setProgressIndicator(ProgressPieIndicator())
                setImageViewFactory(FrescoImageViewFactory())
                showImage(
                    Uri.parse("http://img1.imgtn.bdimg.com/it/u=1520386803,778399414&fm=21&gp=0.jpg"),
                    Uri.parse(url)
                )
            }

            mBtnLoad.setOnClickListener { finish() }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        BigImageViewer.imageLoader().cancelAll()
    }
}