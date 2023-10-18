package bio.medico.patient.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


/**
Created by Samiran Kumar on 14,August,2023
 **/




fun Context.getDrawable1(res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}