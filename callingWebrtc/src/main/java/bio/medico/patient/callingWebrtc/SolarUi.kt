package bio.medico.patient.callingWebrtc

import android.graphics.Path
import android.widget.ImageView
import bio.medico.patient.callingWebrtc.databinding.FragmentCallBinding
import bio.medico.patient.common.AppKeyLog
import bio.medico.patient.data.activityLog.AppLog
import com.theroyalsoft.mydoc.apputil.baseUI.ViewPathAnimator


/**
Created by Samiran Kumar on 25,September,2023
 **/
class SolarUi(
    fragmentCallBinding: FragmentCallBinding,
    private val uiName: String,
    private val closeUi: () -> Unit
) {
    init {

        val ratio = 120f

        val radiusRatio1 = ratio * 1
        val radiusRatio2 = ratio * 2
        val radiusRatio3 = ratio * 3
        val radiusRatio4 = ratio * 4

        val delay1 = 1
        val delay2 = 0
        val delay3 = 1
        val delay4 = 0

        fragmentCallBinding.apply {

            drawPath(imgVDoctor2, radiusRatio1, delay1, -1f, -2f)
            drawPath(imgVDoctor3, radiusRatio2, delay2, 3f, 1f)
            drawPath(imgVDoctor4, radiusRatio3, delay3, 2f, 3f)
            drawPath(imgVDoctor5, radiusRatio4, delay4, 2f, 1f)

            llRoundDashView.setShapeRadiusRatio(
                radiusRatio1,
                radiusRatio2,
                radiusRatio3,
                radiusRatio4
            )

            llCancel.setOnClickListener {
                AppLog.sendButton(uiName, AppKeyLog.CLOSE_CALL_UI)
                //FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_HANGUP_CLICK)
                closeUi.invoke()
            }


            /*ImageLode.lodeImage(imgVDoctor1, url);
        ImageLode.lodeImage(imgVDoctor2, url);
        ImageLode.lodeImage(imgVDoctor3, url);
        ImageLode.lodeImage(imgVDoctor4, url);
        ImageLode.lodeImage(imgVDoctor5, url);*/

            //setImage(imgVDoctor1, R.drawable.ic_male_doctor);
            setImage(imgVDoctor2, bio.medico.patient.assets.R.drawable.ic_female_doctor)
            setImage(imgVDoctor3, bio.medico.patient.assets.R.drawable.ic_male_doctor)
            setImage(imgVDoctor4, bio.medico.patient.assets.R.drawable.ic_female_doctor)
            setImage(imgVDoctor5, bio.medico.patient.assets.R.drawable.ic_female_doctor)
        }

    }

    private fun drawPath(imageView: ImageView?, radius: Float, delay: Int, x: Float, y: Float) {
        val path4 = Path()
        path4.addCircle(x, y, radius, Path.Direction.CW)
        ViewPathAnimator.animate(imageView, path4, delay, 3)
    }

    private fun setImage(imgVIcon: ImageView, image: Int) {
        imgVIcon.setImageResource(image)
    }
}