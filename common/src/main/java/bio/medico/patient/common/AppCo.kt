package bio.medico.patient.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


/**
Created by Samiran Kumar on 11,September,2023
 **/


fun String.isEnglishLanguage(): Boolean {
    return this == AppKey.LANGUAGE_BN
}


fun Activity.goDialerUI(mobileNumber: String) {
    val intent1 = Intent(Intent.ACTION_DIAL)
    intent1.data = Uri.parse("tel:$mobileNumber")
    startActivity(intent1)
}


@Throws(IOException::class)
fun Context.getImageFile(): File? {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"

    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        imageFileName,  /* prefix */
        ".jpg",  /* suffix */
        storageDir /* directory */
    )

}

fun Context.getFileUri(file: File): Uri {
    return FileProvider.getUriForFile(
        this,
        "com.example.android.fileprovider",
        file
    )
}
