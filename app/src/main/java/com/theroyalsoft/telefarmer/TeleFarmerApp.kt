package com.theroyalsoft.telefarmer

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Pritom Dutta on 20/5/23.
 */
@HiltAndroidApp
class TeleFarmerApp : Application() {


    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object{
        @JvmStatic var appContext: Context? = null
    }


}