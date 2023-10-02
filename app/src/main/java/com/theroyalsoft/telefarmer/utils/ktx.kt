package com.theroyalsoft.telefarmer.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import com.google.android.material.color.MaterialColors.isColorLight
import com.theroyalsoft.telefarmer.R
/**
 * Created by Pritom Dutta on 20/5/23.
 */

//fun Activity.applyTransparentStatusBarAndNavigationBar() {
//    window.apply {
//        setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
//    }
//}

fun Activity.hideNavigationBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val window = window
        window.apply {
            setDecorFitsSystemWindows(true)
            decorView.windowInsetsController!!.hide(WindowInsets.Type.navigationBars())
            decorView.windowInsetsController!!.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

fun Activity.navigationBarColor() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val decorView = window.decorView
        var flags = decorView.systemUiVisibility
        flags = if (isColorLight(getColor(R.color.app_color))) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        decorView.systemUiVisibility = flags
    }
    window.navigationBarColor = getColor(R.color.app_color)
}

fun Activity.applyTransparentStatusBarAndNavigationBar() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
    navigationBarColor()
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//    } else {
//        window.setDecorFitsSystemWindows(false)
//    }
}

fun View.isGone(){
    visibility = View.GONE
}

fun View.isVisible(){
    visibility = View.VISIBLE
}

fun View.isInvisible(){
    visibility = View.INVISIBLE
}