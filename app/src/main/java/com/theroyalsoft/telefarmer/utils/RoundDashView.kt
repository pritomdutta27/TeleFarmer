package com.theroyalsoft.telefarmer.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by Pritom Dutta on 31/7/23.
 */
class RoundDashView : View {
    private var roundShape1: RoundShape? = null
    private var roundShape2: RoundShape? = null
    private var roundShape3: RoundShape? = null
    private var roundShape4: RoundShape? = null
    private var ratioRadius1 = 0f
    private var ratioRadius2 = 0f
    private var ratioRadius3 = 0f
    private var ratioRadius4 = 0f

    constructor(context: Context?) : super(context) {
        initMyView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initMyView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initMyView()
    }

    fun initMyView() {
        roundShape1 = RoundShape()
        roundShape2 = RoundShape()
        roundShape3 = RoundShape()
        roundShape4 = RoundShape()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width
        val h = height
        if (w == 0 || h == 0) {
            return
        }
        val x = w.toFloat() / 2.0f
        val y = h.toFloat() / 2.0f
        roundShape1!!.setCircle(x, y, ratioRadius1, Path.Direction.CCW)
        roundShape2!!.setCircle(x, y, ratioRadius2, Path.Direction.CCW)
        roundShape3!!.setCircle(x, y, ratioRadius3, Path.Direction.CCW)
        roundShape4!!.setCircle(x, y, ratioRadius4, Path.Direction.CCW)
        canvas.drawPath(roundShape1!!.getPath(), roundShape1!!.getPaint())
        canvas.drawPath(roundShape2!!.getPath(), roundShape2!!.getPaint())
        canvas.drawPath(roundShape3!!.getPath(), roundShape3!!.getPaint())
        canvas.drawPath(roundShape4!!.getPath(), roundShape4!!.getPaint())
    }

    fun setShapeRadiusRatio(ratio1: Float, ratio2: Float, ratio3: Float, ratio4: Float) {
        ratioRadius1 = ratio1
        ratioRadius2 = ratio2
        ratioRadius3 = ratio3
        ratioRadius4 = ratio4
    }
}