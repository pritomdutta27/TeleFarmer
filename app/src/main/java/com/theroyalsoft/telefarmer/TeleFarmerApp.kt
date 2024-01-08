package com.theroyalsoft.telefarmer

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import bio.medico.patient.common.DeviceIDUtil
import com.theroyalsoft.mydoc.apputil.AppUtilConfig
import com.theroyalsoft.telefarmer.utils.DeviceIDUtil1
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Pritom Dutta on 20/5/23.
 */
@HiltAndroidApp
class TeleFarmerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        AppUtilConfig.init(this, "MPL", "CASH_DATA_MEDICO_PATIENT")

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DeviceIDUtil.setData(
            DeviceIDUtil1.getDeviceID(this),
            DeviceIDUtil1.getDeviceName(),
            DeviceIDUtil1.getAppVersion(),
            DeviceIDUtil1.getOsVersion(),
            DeviceIDUtil1.getAppBuildVersionCode()
        )
    }

    companion object {
        @JvmStatic
        var appContext: Context? = null
    }


}