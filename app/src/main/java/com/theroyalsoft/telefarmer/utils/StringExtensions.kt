package com.theroyalsoft.telefarmer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

/**
 * Created by Pritom Dutta on 5/8/23.
 */


@SuppressLint("HardwareIds")
fun String.getDeviceId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}