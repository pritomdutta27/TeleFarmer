package bio.medico.patient.callingWebrtc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.theroyalsoft.mydoc.apputil.baseUI.RoundShape;

/**
 * Created by Pritom Dutta on 15/10/23.
 */
public class RoundDashView extends View {

    private RoundShape roundShape1;
    private RoundShape roundShape2;
    private RoundShape roundShape3;
    private RoundShape roundShape4;


    private float ratioRadius1;
    private float ratioRadius2;
    private float ratioRadius3;
    private float ratioRadius4;

    public RoundDashView(Context context) {
        super(context);
        initMyView();
    }

    public RoundDashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public RoundDashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView();
    }

    public void initMyView() {
        roundShape1 = new RoundShape();
        roundShape2 = new RoundShape();
        roundShape3 = new RoundShape();
        roundShape4 = new RoundShape();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        if ((w == 0) || (h == 0)) {
            return;
        }

        float x = (float) w / 2.0f;
        float y = (float) h / 2.0f;


        roundShape1.setCircle(x, y, ratioRadius1, Path.Direction.CCW);
        roundShape2.setCircle(x, y, ratioRadius2, Path.Direction.CCW);
        roundShape3.setCircle(x, y, ratioRadius3, Path.Direction.CCW);
        roundShape4.setCircle(x, y, ratioRadius4, Path.Direction.CCW);

        canvas.drawPath(roundShape1.getPath(), roundShape1.getPaint());
        canvas.drawPath(roundShape2.getPath(), roundShape2.getPaint());
        canvas.drawPath(roundShape3.getPath(), roundShape3.getPaint());
        canvas.drawPath(roundShape4.getPath(), roundShape4.getPaint());
    }


    public void setShapeRadiusRatio(float ratio1, float ratio2, float ratio3, float ratio4) {

        ratioRadius1 = ratio1;
        ratioRadius2 = ratio2;
        ratioRadius3 = ratio3;
        ratioRadius4 = ratio4;
    }


}