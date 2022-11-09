package com.example.soundlightrgb.view.customView.loadingDialog

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.soundlightrgb.R
import com.example.soundlightrgb.databinding.LoadingDialogBinding

class LoadingDialog(val activity: Activity) {
    private var binding: LoadingDialogBinding = LoadingDialogBinding.inflate(LayoutInflater.from(activity))

    private var alertDialog: AlertDialog = AlertDialog.Builder(activity, R.style.DialogTheme).apply {
        setView(binding.root)
        setCancelable(false)
    }.create()

    fun setText(text: String) {
        binding.textView.text = text
    }

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