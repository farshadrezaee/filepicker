package com.farshad.filepicker

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList

open class BaseActivity : AppCompatActivity(){

    private lateinit var onPermissionsResultListener: OnPermissionsResultListener

    companion object {
        private const val PERMISSION_REQUEST_ID = 220
    }

    fun requestPermissions(requestPermissions: Array<String>, listener: OnPermissionsResultListener) {
        this.onPermissionsResultListener = listener
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.onPermissionsResultListener!!.onAllow("Allows All Permissions (Current SDK Version )")
            return
        }
        val permissions = ArrayList<String>()

        for (permission in requestPermissions) {
            if (checkPermission(permission)) {
                this.onPermissionsResultListener!!.onAllow(permission)
                continue
            }
            permissions.add(permission)
        }
        if (permissions.size == 0) {
            return
        }
        requestPermissions(permissions.toTypedArray(), PERMISSION_REQUEST_ID)
    }

    private fun checkPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            true
        } else checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_REQUEST_ID) {
            return
        }
        for (i in permissions.indices) {
            val permission = permissions[i]
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                this.onPermissionsResultListener!!.onAllow(permission)
                continue
            }
            this.onPermissionsResultListener!!.onDeny(permission)
        }
    }

    fun showMsg(msg: String?) {
        if (!this.isFinishing && msg != null)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showMsg(@StringRes id: Int) {
        showMsg(getString(id))
    }

}