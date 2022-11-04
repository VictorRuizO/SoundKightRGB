package com.example.soundlightrgb.view.customView.loadingDialog

import android.app.Activity
import android.app.AlertDialog
import com.example.soundlightrgb.R

class LoadingDialog(val activity: Activity) {
    private var alertDialog: AlertDialog = AlertDialog.Builder(activity, R.style.DialogTheme).apply {
        setView(activity.layoutInflater.inflate(R.layout.loading_dialog, null))
        setCancelable(false)
    }.create()

    fun show() {
        if (!alertDialog.isShowing)
            alertDialog.show()
    }
    fun dismiss() {
        alertDialog.dismiss()
    }

    companion object {
        fun make(activity: Activity): LoadingDialog {
            return LoadingDialog(activity)
        }
    }
}