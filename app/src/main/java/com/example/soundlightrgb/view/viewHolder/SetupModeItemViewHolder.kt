package com.example.soundlightrgb.view.viewHolder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.soundlightrgb.databinding.ModeSetupItemBinding
import com.example.soundlightrgb.util.toEditable
import com.example.soundlightrgb.view.model.ModeItemModel

class SetupModeItemViewHolder(
    private val binding: ModeSetupItemBinding,
    private val textChanged: (index: String, name: String) -> Unit
): RecyclerView.ViewHolder(binding.root), TextWatcher{
    private lateinit var item: ModeItemModel
    fun bind(
        item: ModeItemModel,
        delete: () -> Unit,
    ) {
        this.item = item
        binding.indexTextView.text = "# ${item.index}"
        binding.nameModeEditText.removeTextChangedListener(this)
        binding.nameModeEditText.setText(item.name)
        binding.nameModeEditText.addTextChangedListener(this)
        binding.deleteButton.visibility = if (item.delete)
            View.VISIBLE
        else
            View.GONE
        binding.deleteButton.setOnClickListener { delete() }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        textChanged(item.index, s.toString())
    }

}