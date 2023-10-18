package bio.medico.patient.callingWebrtc.freeCall

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import bio.medico.patient.callingWebrtc.databinding.DialogFreeCallWarningBinding
import com.skh.hkhr.util.view.OnSingleClickListener
import timber.log.Timber

/**
 * ..................................................................
 * Created by Samiran Kumar on 14/03/22.
 * Copyright (c) 2020
 * ..................................................................
 */
class FreeCallWarningDialog(context: Context, private val listener: () -> Unit) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    private lateinit var binding: DialogFreeCallWarningBinding

    fun showDialog() {
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

       // window!!.setBackgroundDrawable(AppResources.call_bg_color)
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = DialogFreeCallWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleEn = "Subscription"
        val titleBn = "সাবস্ক্রিপশন"
        val messageBn =
            "প্রিয় গ্রাহক, আপনি এতক্ষন আমাদের ফ্রি কলে ছিলেন। সার্ভিসটি যদি ভালো লেগে থাকে, তবে যে কোন একটি প্যাক ক্রয় করে পূর্নাঙ্গ সেবা গ্রহন করতে পারবেন।"
        val messageEn =
            "Dear Customer, you have been on our free call. If you like the service, you can purchase any of the packs to get full consultation."
        val buyPackEn = "Buy pack"
        val buyPackBn = "প্যাক কিনুন"

        binding.apply {

//            if (LocalData.getLanguage() == AppKey.LANGUAGE_BN) {
//                tvDialogHeader.text = titleBn
//                tvDialogMessage.text = messageBn
//                tvYes.text = buyPackBn
//            } else {
                tvDialogHeader.text = titleEn
                tvDialogMessage.text = messageEn
                tvYes.text = buyPackEn
//            }

            tvCancel.text = ""


            tvCancel.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) {
                    Timber.e("onNegative")
                }
            })


            tvYes.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) {
//                    AppLog.sendButton(
//                        AppKeyLog.UI_FREE_CALL_WARNING,
//                        "Click and Go >> Buy Pack UI"
//                    )
                    Timber.e("goBuyPack")
                    listener.invoke()
                }
            })
        }

        //AppLog.sendUiOpen(AppKeyLog.UI_FREE_CALL_WARNING)
        show()

    }

    fun hideDialog() {
        dismiss()
    }

    //==================================================================================
    override fun onBackPressed() {}

    //=============================================================================
}