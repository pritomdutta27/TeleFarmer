package com.theroyalsoft.telefarmer.utils

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path

/**
 * Created by Pritom Dutta on 31/7/23.
 */
class RoundShape {
    private val paint: Paint
    private val path: Path

    init {
        paint = Paint()
        paint.strokeWidth = 5f
        paint.color = Color.parseColor("#0098C9")
        paint.style = Paint.Style.STROKE
        paint.pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 20f)
        path = Path()
    }

    fun setCircle(x: Float, y: Float, radius: Float, dir: Path.Direction?) {
        path.reset()
        path.addCircle(x, y, radius, dir!!)
    }

    fun getPath(): Path {
        return path
    }

    fun getPaint(): Paint {
        return paint
    }
}
