package com.farshad.filepicker

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseActivity() {

    private var fileSize: Double = 0.0
    private lateinit var filePath: String
    private lateinit var fileName: String
    private lateinit var file: File

    companion object {
        const val ATTACH_FILE_REQUEST_CODE = 9999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectFileTextView.setOnClickListener {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                object : OnPermissionsResultListener {
                    override fun onAllow(permission: String) {
                        fileChooserIntent(ATTACH_FILE_REQUEST_CODE)
                    }

                    override fun onDeny(permission: String) {
                        showMsg(R.string.error_need_permission_write_external_storage_attach_file)
                    }
                })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK || data == null) {
            return
        }

        // Handle choose file
        if (requestCode == ATTACH_FILE_REQUEST_CODE) {
            filePath = FileHelper.getPath(data.data!!, Build.VERSION.SDK_INT, this)
            fileName = FileHelper.getName(filePath)
            fileSize = FileHelper.getSize(filePath)
            file = File(filePath)

            filePathTextView.text = "path = $filePath"
            fileNameTextView.text = "name = $fileName"
            fileSizeTextView.text = "size = $fileSize mg"
        }

    }

}
