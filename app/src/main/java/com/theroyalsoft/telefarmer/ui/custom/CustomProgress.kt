package com.theroyalsoft.telefarmer.ui.custom

import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import com.skh.hkhr.util.AppRes
import com.theroyalsoft.telefarmer.R

/**
 * Created by Pritom Dutta on 27/11/23.
 */
class CustomProgress {
    private fun showProgress(context: Context, message: String, cancelable: Boolean) {
        mDialog = Dialog(context)
        mDialog!!.window!!.setBackgroundDrawableResource(bio.medico.patient.assets.R.color.transparent)

        // no tile for the dialog
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog!!.setContentView(R.layout.prograss_bar_dialog)
        mProgressBar = mDialog!!.findViewById<View>(R.id.progress_bar) as ProgressBar
        //  mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources()
        // .getColor(R.color.material_blue_gray_500), PorterDuff.Mode.SRC_IN);
        val progressText = mDialog!!.findViewById<View>(R.id.progress_text) as TextView
        progressText.text = "" + message
        progressText.visibility = View.GONE
        mProgressBar!!.visibility = View.VISIBLE
        // you can change or add this line according to your need
        mProgressBar!!.isIndeterminate = true
        mDialog!!.setCancelable(cancelable)
        mDialog!!.setCanceledOnTouchOutside(cancelable)
        mDialog!!.show()
    }

    companion object {
        var customProgress: CustomProgress? = null
        private var mDialog: Dialog? = null
        var mProgressBar: ProgressBar? = null
        private val instance: CustomProgress?
            private get() {
                if (customProgress == null) {
                    customProgress = CustomProgress()
                }
                return customProgress
            }

        fun showProgress(context: Context?) {
            try {
                hideProgress()
                if (mDialog == null) {
                    instance
                }
                mDialog = Dialog(context!!)
                mDialog?.window?.setBackgroundDrawableResource(bio.medico.patient.assets.R.color.transparent)

                // no tile for the dialog
                mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog?.setContentView(R.layout.prograss_bar_dialog)
                mProgressBar = mDialog!!.findViewById<View>(R.id.progress_bar) as ProgressBar
                mProgressBar!!.indeterminateDrawable
                    .setColorFilter(
                        AppRes.getColor(
                            bio.medico.patient.assets.R.color.color_app,
                            context
                        ), PorterDuff.Mode.SRC_IN
                    )
                val progressText = mDialog!!.findViewById<View>(R.id.progress_text) as TextView
                progressText.text = ""
                progressText.visibility = View.GONE
                mProgressBar!!.visibility = View.VISIBLE
                // you can change or add this line according to your need
                mProgressBar!!.isIndeterminate = true
                mDialog!!.setCancelable(false)
                mDialog!!.setCanceledOnTouchOutside(false)
                mDialog!!.show()
            } catch (e: Exception) {
                //ApiManager.sendApiLogErrorApi(e, AppKeyLog.NA)
            }
        }

        fun hideProgress() {
            if (mDialog != null) {
                mDialog!!.dismiss()
                mDialog = null
            }
        }
    }
}
