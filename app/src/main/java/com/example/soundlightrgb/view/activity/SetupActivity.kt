package com.example.soundlightrgb.view.activity

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soundlightrgb.R
import com.example.soundlightrgb.databinding.ActivitySetupBinding
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.util.EMPTY_STRING
import com.example.soundlightrgb.view.adapter.SetupModeAdapter
import com.example.soundlightrgb.view.customView.loadingDialog.LoadingDialog
import com.example.soundlightrgb.view.customView.warningSnackbar.WarningSnackbar
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.model.VariableItemModel
import com.example.soundlightrgb.view.viewmodel.SetupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupActivity : AppCompatActivity() {

    private val viewModel: SetupViewModel by viewModels()
    private lateinit var binding: ActivitySetupBinding
    private lateinit var modeAdapter: SetupModeAdapter

    private lateinit var warningSnackbar: WarningSnackbar
    private lateinit var loader: LoadingDialog

    private var stateShowToken = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = LoadingDialog.make(this)
        viewModel.init()
        setAdapters()
        setListeners()
        setObservers()
    }

    private fun getVariablesList(): List<VariableItemModel> {
        return listOf(
            VariableItemModel(VariableType.POWER_VAR, binding.setupVariablesView.powerVarEditText.text.toString()),
            VariableItemModel(VariableType.COLOR_VAR, binding.setupVariablesView.colorVarEditText.text.toString()),
            VariableItemModel(VariableType.BRIGHTNESS_VAR, binding.setupVariablesView.brightnessVarEditText.text.toString()),
            VariableItemModel(VariableType.SPEED_VAR, binding.setupVariablesView.speedVarEditText.text.toString()),
            VariableItemModel(VariableType.VOLUME_VAR, binding.setupVariablesView.volumeVarEditText.text.toString()),
            VariableItemModel(VariableType.MODE_VAR, binding.setupVariablesView.modeVarEditText.text.toString()),
            VariableItemModel(VariableType.WHITE_POWER_VAR, binding.setupVariablesView.whitePowerVarEditText.text.toString()),
            VariableItemModel(VariableType.WHITE_BRIGHTNESS_VAR, binding.setupVariablesView.whiteBrightnessVarEditText.text.toString()),
        )
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

    private fun setListeners() {
        binding.addModeButton.setOnClickListener {
            modeAdapter.addMode()
        }
        binding.saveButton.setOnClickListener {
            viewModel.saveData(
                binding.tokenEditText.text.toString(),
                getVariablesList(),
                modeAdapter.getItems().filter { it.name != EMPTY_STRING }
            )
        }
        binding.backImageView.setOnClickListener {
            finish()
        }
        binding.showTokenImageView.setOnClickListener {
            if (stateShowToken) {
                binding.showTokenImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_invisible_eye))
                binding.tokenEditText.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                binding.showTokenImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visible_eye))
                binding.tokenEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            stateShowToken = !stateShowToken
        }
    }

    private fun setAdapters() {
        modeAdapter = SetupModeAdapter(mutableListOf())
        binding.modesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SetupActivity)
            setHasFixedSize(false)
            adapter = modeAdapter
        }
    }

    private fun setObservers() {
        viewModel.token.observe(this) { binding.tokenEditText.setText(it) }
        viewModel.variables.observe(this) {
            binding.setupVariablesView.powerVarEditText.setText(
                it.first { it.type == VariableType.POWER_VAR }.value
            )
            binding.setupVariablesView.brightnessVarEditText.setText(
                it.first { it.type == VariableType.BRIGHTNESS_VAR }.value
            )
            binding.setupVariablesView.colorVarEditText.setText(
                it.first { it.type == VariableType.COLOR_VAR }.value
            )
            binding.setupVariablesView.speedVarEditText.setText(
                it.first { it.type == VariableType.SPEED_VAR }.value
            )
            binding.setupVariablesView.volumeVarEditText.setText(
                it.first { it.type == VariableType.VOLUME_VAR }.value
            )
            binding.setupVariablesView.modeVarEditText.setText(
                it.first { it.type == VariableType.MODE_VAR }.value
            )
            binding.setupVariablesView.whitePowerVarEditText.setText(
                it.first { it.type == VariableType.WHITE_POWER_VAR }.value
            )
            binding.setupVariablesView.whiteBrightnessVarEditText.setText(
                it.first { it.type == VariableType.WHITE_BRIGHTNESS_VAR }.value
            )
        }
        viewModel.modes.observe(this) {
            if (it.isEmpty()) {
                modeAdapter.setItems(listOf(ModeItemModel("0", "", true)))
            } else {
                modeAdapter.setItems(it)
            }
        }
        viewModel.state.observe(this) {
            if (!it) {
                finish()
            }
        }

        viewModel.loaderVariables.observe(this) {
            if (it) {
                binding.setupVariablesView.root.visibility = View.GONE
                binding.variablesProgressBar.visibility = View.VISIBLE
            } else {
                binding.setupVariablesView.root.visibility = View.VISIBLE
                binding.variablesProgressBar.visibility = View.GONE
            }
        }

        viewModel.loaderModes.observe(this) {
            if (it) {
                binding.modesRecyclerView.visibility = View.GONE
                binding.addModeButton.visibility = View.GONE
                binding.modesProgressBar.visibility = View.VISIBLE
            } else {
                binding.modesRecyclerView.visibility = View.VISIBLE
                binding.addModeButton.visibility = View.VISIBLE
                binding.modesProgressBar.visibility = View.GONE
            }
        }

        viewModel.loader.observe(this) {
            if (it) {
                loader.setText(resources.getString(R.string.setup_save_data_loader_text))
                loader.show()
            } else {
                loader.dismiss()
            }
        }

        viewModel.warning.observe(this) {
            makeWarningSnackbar(resources.getString(it))
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}