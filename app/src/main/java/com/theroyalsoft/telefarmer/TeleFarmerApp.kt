package com.theroyalsoft.telefarmer

import android.app.Application
import android.content.Context
import bio.medico.patient.common.DeviceIDUtil
import com.skh.hkhr.util.BuildConfig
import com.theroyalsoft.mydoc.apputil.AppUtilConfig
import com.theroyalsoft.telefarmer.utils.DeviceIDUtil1
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

/**
 * Created by Pritom Dutta on 20/5/23.
 */
@HiltAndroidApp
class TeleFarmerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
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

    companion object{
        @JvmStatic var appContext: Context? = null
    }


}