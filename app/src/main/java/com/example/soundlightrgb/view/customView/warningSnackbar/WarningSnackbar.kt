package com.example.soundlightrgb.view.customView.warningSnackbar

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import com.example.soundlightrgb.databinding.SnackbarWarningLayoutBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout


class WarningSnackbar private constructor(
    private val builder: Builder,
    private val context: Context,
)  {

    init {
        setCustomSnackbar()
    }

    fun show() {
        with(builder) {
            customSnackbar.show()
            starSnackTimer()
        }
    }

    fun setSubText(subText: String) {
        builder.subText = subText
        setCustomSnackbar()
    }

    private fun setCustomSnackbar() {
        val binding = SnackbarWarningLayoutBinding.inflate(LayoutInflater.from(context))
        with(builder) {
            titleText?.let {
                binding.titleTextView.text = it
            }
            subText?.let {
                binding.errorMessageTextView.text = it
            }
            icon?.let {
                binding.iconImageView.setImageDrawable(
                    context.resources.getDrawable(it, null).apply {
                        iconColor?.let { setTint(context.getColor(it)) }
                    }
                )
            }
            setupSnackbarLayout(binding, customSnackbar)
        }
    }

    private fun setupSnackbarLayout(binding: SnackbarWarningLayoutBinding, customSnackbar: Snackbar) {
        val snackbarLayout = customSnackbar.view as SnackbarLayout
        snackbarLayout.apply {
            addView(binding.root)
            setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
        }
        customSnackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        customSnackbar.anchorView = builder.anchorView
    }

    private fun starSnackTimer() {
        val snackTimer = object : CountDownTimer(SNACK_MILLIS_IN_FUTURE, SNACK_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                builder.customSnackbar.dismiss()
            }

        }
        snackTimer.start()
    }

    class Builder(private val context: Context, private val rootView: View) {
        var anchorView: View? = null
        var titleText: String? = null
        var subText: String? = null
        var icon: Int? = null
        var iconColor: Int? = null
        lateinit var customSnackbar: Snackbar

        fun setAnchorView(anchorView: View): Builder {
            this.anchorView = anchorView
            return this
        }

        fun setTitleText(title: String): Builder {
            this.titleText = title
            return this
        }

        fun setSubText(subText: String): Builder {
            this.subText = subText
            return this
        }

        fun setIcon(icon: Int): Builder {
            this.icon = icon
            return this
        }

        fun setIconColor(color: Int): Builder {
            this.iconColor = color
            return this
        }

        fun build(): WarningSnackbar {
            customSnackbar = Snackbar.make(rootView, SNACK_EMPTY_STATE, Snackbar.LENGTH_INDEFINITE)
            return WarningSnackbar(this, context)
        }
    }

    companion object {
        const val SNACK_MILLIS_IN_FUTURE = 4000L
        const val SNACK_COUNTDOWN_INTERVAL = 1000L
        const val LEFT_PADDING = 8
        const val RIGHT_PADDING = 8
        const val TOP_PADDING = 0
        const val BOTTOM_PADDING = 0
        const val SNACK_EMPTY_STATE = ""
    }

}