package com.example.soundlightrgb.view.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import com.example.soundlightrgb.R
import com.example.soundlightrgb.databinding.ActivityMainBinding
import com.example.soundlightrgb.view.customView.loadingDialog.LoadingDialog
import com.example.soundlightrgb.view.customView.warningSnackbar.WarningSnackbar
import com.example.soundlightrgb.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var warningSnackbar: WarningSnackbar
    private lateinit var loader: LoadingDialog

    private var firstTimeMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = LoadingDialog.make(this)
        binding.colorPickerView.setColor(Color.WHITE, false)
        viewModel.init()

        setAdapters()
        setListeners()
        setObservers()

        // goToSetupActivity()
    }

    private fun makeWarningSnackbar(subText: String) {
        warningSnackbar = WarningSnackbar.Builder(this, binding.root)
            .setAnchorView(binding.snackbarGuideline)
            .setTitleText(resources.getString(R.string.titleWarning))
            .setSubText(subText)
            .setIcon(R.drawable.ic_warning)
            .setIconColor(R.color.warning_red)
            .build()
        warningSnackbar.show()
    }

    private fun setAdapters() {
        ArrayAdapter.createFromResource(
            this,
            R.array.modeLightArray,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            binding.modeSpinner.adapter = adapter
            binding.modeSpinner.setSelection(0, false)
        }

    }

    private fun setObservers() {
        viewModel.powerDevice.observe(this) { value ->
            if (value) {
                binding.powerBackgroundConstraintLayout.setBackgroundResource(R.drawable.ic_baseline_circle_24)
                binding.powerImageView.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_light,
                        null
                    ).apply {
                        setTint(
                            resources.getColor(R.color.second_gray, null)
                        )
                    }
                )
            } else {
                binding.powerBackgroundConstraintLayout.setBackgroundResource(0)
                binding.powerImageView.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_light,
                        null
                    ).apply {
                        setTint(
                            resources.getColor(R.color.secand_white, null)
                        )
                    }
                )
            }
        }

        viewModel.brightness.observe(this) {
            binding.brightSeekBar.progress = it.toInt()
        }

        viewModel.speed.observe(this) {
            binding.speedSeekBar.progress = it.toInt()
        }

        viewModel.volume.observe(this) {
            binding.volumeSeekBar.progress = it.toInt()
        }

        viewModel.mode.observe(this) {
            with(binding.modeSpinner) {
                if (it < adapter.count){
                    firstTimeMode = true
                    setSelection(it)
                }
            }
        }

        viewModel.whitePower.observe(this) {
            binding.whiteSwitch.isChecked = it
        }

        viewModel.whiteBrightness.observe(this) {
            binding.whiteBrightnessSeekBar.progress = it.toInt()
        }

        viewModel.warningSnackbar.observe(this) { subText->
            subText?.let {
                makeWarningSnackbar(it)
            }
        }

        viewModel.loader.observe(this) {
            if (it) {
                loader.show()
            } else {
                loader.dismiss()
            }
        }
    }

    private fun setListeners() {
        binding.powerBackgroundConstraintLayout.setOnClickListener {
            viewModel.powerDevice.value?.let { viewModel.sendPower(!it) }
        }

        binding.colorPickerView.addOnColorSelectedListener {
            viewModel.sendColor(it)
        }

        binding.brightSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    viewModel.sendBrightness(it.progress)
                }
            }

        })

        binding.speedSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    viewModel.sendSpeed(it.progress)
                }
            }

        })

        binding.volumeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    viewModel.sendVolume(it.progress)
                }
            }

        })

        binding.modeSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!firstTimeMode) {
                    viewModel.sendMode(position)
                }
                firstTimeMode = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        binding.whiteSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.sendWhitePower(isChecked)
        }

        binding.whiteBrightnessSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    viewModel.sendWhiteBrightness(it.progress.toDouble())
                }
            }

        })
    }

    private fun goToSetupActivity() {
        Intent(this, SetupActivity::class.java).apply {
            startActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}