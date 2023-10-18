package bio.medico.patient.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import bio.medico.patient.common.databinding.DialogDeleteBinding
import timber.log.Timber


/**
Created by Samiran Kumar on 29,August,2023
 **/
open class DeleteItemConfirmationDialog<T>(
    private val context: Context,
    private val item: T,
    private val onCallback: (t: T) -> Unit
) : Dialog(context, bio.medico.patient.assets.R.style.DialogFadeAnimation) {

    private lateinit var binding: DialogDeleteBinding

    fun showDialog(language:String) {
        setCancelable(true)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("Open DeleteItemConfirmationDialog")

        binding.apply {


//            tvTitle.text = language.getBngEnglishText(Vt.Delete)
//            tvBody.text = language.getBngEnglishText(Vt.AreYouSure)
//            tvYes.text = language.getBngEnglishText(Vt.Yes)
//            tvCancel.text = language.getBngEnglishText(Vt.Cancel)


            tvYes.setOnClickListener {
                hideDialog()
                onCallback.invoke(item)
            }

            tvCancel.setOnClickListener { hideDialog() }

        }


        //------------------------------------------------------------------------------------------
        show()
    }


    private fun hideDialog() {
        dismiss()
    }


    //=============================================================================
    override fun onBackPressed() {
        Timber.e("onBackPressed")
        hideDialog()
    }


    //==============================================================================================


}