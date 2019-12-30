package com.farshad.filepicker

import android.content.Intent
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Farshad Rezaei.
 */

fun AppCompatActivity.fileChooserIntent(requestCode: Int, vararg mimeTypes:String) {

    val intent = Intent(Intent.ACTION_GET_CONTENT)

    if (SDK_INT >= KITKAT) {

        intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
        if (mimeTypes.isNotEmpty()) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }

    } else {

        var mimeTypesStr = ""
        for (mimeType in mimeTypes) {
            mimeTypesStr += "$mimeType|"
        }

        if (mimeTypesStr.isEmpty()) {
            intent.type = "file/*"
        }else{
            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
        }

    }

    startActivityForResult(Intent.createChooser(intent, getString(R.string.choose_file)), requestCode)

}
