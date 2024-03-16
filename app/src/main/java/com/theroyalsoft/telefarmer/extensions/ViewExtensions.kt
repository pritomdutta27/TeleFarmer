package com.theroyalsoft.telefarmer.extensions

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.helper.EqualSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.GridSpacingItemDecoration
import com.theroyalsoft.telefarmer.helper.RecyclerViewItemDecoration
import com.zhpan.indicator.IndicatorView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Pritom Dutta on 28/8/23.
 */


fun View.hide() {
////    animate().alpha(0.0f).duration = 1000
//    animate().translationY(0f).duration = 100
////

    animate()
//        .translationX(width.toFloat())
        .alpha(0.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.GONE
            }
        })

}

fun View.show() {
//    animate().translationY(height.toFloat()).duration = 100
    animate()
//        .translationX(0f)
        .alpha(1.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.VISIBLE
            }
        })
//    visibility = View.VISIBLE
//    animate().alpha(1.0f).duration = 1000
}


fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long) {
    this@typeWrite.text = ""
    lifecycleOwner.lifecycleScope.launch {
        repeat(text.length) {
            delay(intervalMs)
            this@typeWrite.text = text.take(it + 1)
        }
    }
}


fun ViewPager2.autoScroll(
    size: Int,
    count: Int,
    indicator: IndicatorView,
    handler: Handler?,
    mTimer: Timer?
) {
    var currentPage = count
    val update: Runnable? = Runnable {
        if (currentPage == size) {
            currentPage = 0
        }
        setCurrentItem(currentPage++, true)
    }

    val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            currentPage = position
            indicator.onPageSelected(position)
        }
    }
    registerOnPageChangeCallback(pageChangeCallback)

    mTimer?.schedule(object : TimerTask() {
        override fun run() {
            handler?.post(update!!)
        }
    }, 1000, 4000)
}

fun ImageView.setImage(url: String) {
    Timber.e("Image: $url")
    Glide
        .with(this)
        .load(url)
        .placeholder(R.drawable.app_icon_green)
        .into(this);
}

fun ImageView.setCenterCropImage(url: String) {
    Timber.e("Image: $url")
    Glide
        .with(this)
        .load(url)
        .placeholder(R.drawable.app_icon_green)
        .centerCrop()
        .into(this);
}

fun ImageView.setImageProfile(url: String) {
    Timber.e("Image: $url")
    Glide
        .with(this)
        .load(url)
        .error(R.drawable.avatar_man)
        .into(this);

}

fun RecyclerView.setItemDecorationSpacingDivider(spacingPx: Float) {
    val divider = RecyclerViewItemDecoration(spacingPx.toInt(), context)
    addItemDecoration(divider)
}

fun RecyclerView.setItemDecorationSpacing(spacingPx: Float) {
    val divider = EqualSpacingItemDecoration(spacingPx.toInt())
    addItemDecoration(divider)
}

fun RecyclerView.setItemDecorationGrid(spanCount: Int, spacing: Int, includeEdge: Boolean) {
    val divider = GridSpacingItemDecoration(spanCount, spacing, includeEdge)
    addItemDecoration(divider)
}

fun View?.fitSystemWindowsAndAdjustResize() = this?.let { view ->
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
        view.fitsSystemWindows = true
        val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

        WindowInsetsCompat
            .Builder()
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(0, 0, 0, bottom)
            )
            .build()
            .apply {
                ViewCompat.onApplyWindowInsets(v, this)
            }
    }
}