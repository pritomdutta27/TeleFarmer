package bio.medico.patient.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.View.TEXT_ALIGNMENT_INHERIT
import bio.medico.patient.common.R
import bio.medico.patient.common.databinding.DialogConfirmBinding
import com.skh.hkhr.util.view.OnSingleClickListener
import timber.log.Timber


/**
Created by Samiran Kumar on 29,August,2023
 **/
class ConfirmDialog(
    context: Context
) : Dialog(context, bio.medico.patient.assets.R.style.DialogFadeAnimation) {

    private lateinit var binding: DialogConfirmBinding

    fun showDialog(
        headerText: String,
        message: String,
        positiveText: String,
        negativeText: String,
        iMessageCallback: IMessageCallback
    ) {
        setCancelable(true)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("Open ConfirmDialog")

        binding.apply {


            tvDialogHeader.text = headerText
            tvDialogMessage.text = message
            tvCancel.text = negativeText
            tvYes.text = positiveText

            tvCancel.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) {
                    Timber.e("onNegative")
                    iMessageCallback.onNegative()
                    hideDialog()
                }
            })


            tvYes.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) {
                    Timber.e("onPositive")
                    iMessageCallback.onPositive()
                    hideDialog()
                }
            })
        }


        //------------------------------------------------------------------------------------------
        show()
    }

    fun setTextAlignment() {
        binding.tvDialogMessage.textAlignment = TEXT_ALIGNMENT_INHERIT;
    }

    fun hideDialog() {
        dismiss()
    }


    //=============================================================================
    override fun onBackPressed() {
        Timber.e("onBackPressed")
        hideDialog()
    }


    //==============================================================================================

    //==============================================================================================
    interface IMessageCallback {
        fun onPositive()
        fun onNegative()
    }


}