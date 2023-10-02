package com.theroyalsoft.mydoc.apputil.baseUI;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

public class RoundShape {
    private Paint paint;
    private Path path;

    public RoundShape() {
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#0098C9"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] {5f,5f}, 20f));


        path = new Path();
    }

    public void setCircle(float x, float y, float radius, Path.Direction dir) {
        path.reset();
        path.addCircle(x, y, radius, dir);
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {
        return paint;
    }

}
