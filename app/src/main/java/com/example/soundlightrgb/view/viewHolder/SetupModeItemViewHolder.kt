package com.example.soundlightrgb.view.viewHolder

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.soundlightrgb.databinding.ModeSetupItemBinding
import com.example.soundlightrgb.util.toEditable
import com.example.soundlightrgb.view.model.ModeItemModel

class SetupModeItemViewHolder(
    private val binding: ModeSetupItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: ModeItemModel,
        delete: () -> Unit,
        textChanged: (index: String, name: String) -> Unit
    ) {
        binding.indexTextView.text = "# ${item.index}"
        binding.powerVarEditText.text.replace(0, 0, item.name)
        binding.powerVarEditText.doOnTextChanged { text, _, _, _ ->
            textChanged(item.index, text.toString())
        }
        binding.deleteButton.visibility = if (item.delete)
            View.VISIBLE
        else
            View.GONE
        binding.deleteButton.setOnClickListener { delete() }
    }

}