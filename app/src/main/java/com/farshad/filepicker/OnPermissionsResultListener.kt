package com.farshad.filepicker

interface OnPermissionsResultListener {
    fun onAllow(permission: String)

    fun onDeny(permission: String)
}

