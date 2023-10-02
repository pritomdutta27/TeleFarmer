package com.theroyalsoft.telefarmer.extensions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

/**
 * Created by Pritom Dutta on 28/8/23.
 */


fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.replaceFirstChar(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

fun String.isMobileNumberValid(): Boolean {
    val regexStr = "(^([+]{1}[8]{2}|88)?(01){1}[3-9]{1}\\d{8})\$"
    return Pattern.compile(regexStr).matcher(this).matches();
}

fun String.getFromDateTime(dateFormat: String, outFormat: String): String? {
    val input = SimpleDateFormat(dateFormat)
    val output = SimpleDateFormat(outFormat)
    try {
        val getAbbreviate = input.parse(this)    // parse input
        return output.format(getAbbreviate)    // format output
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}

fun String.getBearerToken(): String = "Bearer " + this

fun Context.setFormXmlText(id: Int, formatArgs: String): Spanned {
    return Html.fromHtml(getString(id, formatArgs), HtmlCompat.FROM_HTML_MODE_LEGACY)
}


//fun String.FormXmlText(@StringRes id: Int): CharSequence =
//    setFormXmlText(id).toSpanned().toHtml().format(format(this)).parseAsHtml()

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}