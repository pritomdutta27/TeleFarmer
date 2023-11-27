package com.theroyalsoft.telefarmer.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import bio.medico.patient.common.AppKey
import com.permissionx.guolindev.PermissionX
import com.skh.hkhr.util.log.ToastUtil
import com.theroyalsoft.telefarmer.R

/**
 * Created by Pritom Dutta on 28/8/23.
 */

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
    ))
}

fun Context.getString(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }

fun Context.getStringWithArg(@StringRes resId: Int?, arg: String) =
    resId?.let {
        getString(it, arg)
    } ?: run {
        ""
    }

fun Context.getStringWithTwoArg(@StringRes resId: Int?, arg: String, argTwo: String) =
    resId?.let {
        getString(it, arg, argTwo)
    } ?: run {
        ""
    }

fun Context.getHtmlText(@StringRes resId: Int?, arg: String) =
    resId?.let {
        val text = getString(it, arg)
        val dynamicText = String.format(text, "placeholder1")
        val dynamicStyledText =
            HtmlCompat.fromHtml(dynamicText, HtmlCompat.FROM_HTML_MODE_COMPACT)
        dynamicStyledText
    } ?: run {
        ""
    }

fun Context.showFromStringToast(@StringRes resId: Int?, howLong: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, getString(resId), howLong).show()

fun Context.showToast(message: String, howLong: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, howLong).show()


@SuppressLint("HardwareIds")
fun Context.getPhoneDeviceId(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

fun Context.getDeviceName(): String = android.os.Build.MODEL

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Context.openLogout(
    onClick: () -> Unit
) {
    val dialog = Dialog(this)
    dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.logout_dialog)
    val dialogButton = dialog.findViewById<AppCompatButton>(R.id.btn_close)
    val btn = dialog.findViewById<AppCompatButton>(R.id.btn_login)


    btn.setSafeOnClickListener { v: View? ->
        dialog.dismiss()
        Handler(Looper.getMainLooper()).postDelayed({
            onClick.invoke()
        }, 200)
    }
    dialogButton.setSafeOnClickListener { v: View? ->
        dialog.dismiss()
    }
    dialog.show()
}


fun Fragment.getCameraAndMicPermission(success: () -> Unit) {
    PermissionX.init(this)
        .permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO)
        .request { allGranted, _, _ ->
            if (allGranted) {
                success()
            } else {
                Toast.makeText(
                    requireContext(),
                    "camera and mic permission is required",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
}

fun Fragment.getCameraAndPhotoPermission(success: () -> Unit) {
    PermissionX.init(this)
        .permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        .request { allGranted, _, _ ->
            if (allGranted) {
                success()
            } else {
                Toast.makeText(
                    requireContext(),
                    "camera and mic permission is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

fun Int.convertToHumanTime(): String {
    val seconds = this % 60
    val minutes = this / 60
    val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
    val minutesString = if (minutes < 10) "0$minutes" else "$minutes"
    return "$minutesString:$secondsString"
}

fun Context.showLoadingDialog(): Dialog {
    val dialog = Dialog(this)
    dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.progress_dialog)
//    dialog.show()
    return dialog
}

fun Context.checkInternet(
    isShowMessage: Boolean = false
): Boolean {
    val isConnect = isNetworkConnected()
    //  val isConnect = true

    if (!isConnect && isShowMessage) {
        showNoInternetMessage()
    }

    return isConnect
}

fun showNoInternetMessage() {
    ToastUtil.showToastMessage(AppKey.ERROR_INTERNET_CONNECTION)
}