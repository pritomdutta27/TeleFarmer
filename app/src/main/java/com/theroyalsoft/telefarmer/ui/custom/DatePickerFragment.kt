package com.theroyalsoft.telefarmer.ui.custom

import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Date

/**
 * Created by Pritom Dutta on 6/11/23.
 */

typealias ListBottomSheetCallBack<T> = (data: T) -> Unit
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var callBack: ListBottomSheetCallBack<String>? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.Theme_Holo_Light_Dialog_MinWidth,
            this,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//        val inflater: LayoutInflater = requireActivity().getLayoutInflater()
//        datePickerDialog.setView(inflater.inflate(R.layout.date_picker_layout, null))
        return datePickerDialog
    }

    fun setOnClick(callBack: ListBottomSheetCallBack<String>) {
        this.callBack = callBack
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        callBack?.invoke("$year/" + (month + 1) + "/$dayOfMonth")
    }
}