package com.example.soundlightrgb.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soundlightrgb.databinding.ModeSetupItemBinding
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.viewHolder.SetupModeItemViewHolder

class SetupModeAdapter(private var items: MutableList<ModeItemModel>): RecyclerView.Adapter<SetupModeItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetupModeItemViewHolder {
        val binding = ModeSetupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SetupModeItemViewHolder(binding) { index, name -> onNameChanged(index, name) }
    }

    override fun onBindViewHolder(holder: SetupModeItemViewHolder, position: Int) {
        holder.bind(items[position]) { deleteLastMode() }
    }

    fun addMode() {
        if (items.lastOrNull()?.name  != "") {
            val modeItem = ModeItemModel(items.count().toString(), "", true)
            items.forEach { it.delete = false }
            items.add(modeItem)
            notifyItemChanged(itemCount - 2)
            notifyItemInserted(itemCount - 1)
        }
    }

    private fun deleteLastMode() {
        items.removeLast()
        items.lastOrNull()?.delete = true
        notifyItemRemoved(itemCount)
    }

    private fun onNameChanged(index: String, name: String) {
        items.find { it.index == index }?.name = name
    }

    fun setItems(items: List<ModeItemModel>) {
        this.items.apply {
            clear()
            addAll(items)
            lastOrNull()?.delete = true
        }
        notifyItemRangeInserted(0, itemCount)
    }

    override fun getItemCount(): Int = items.count()

    fun getItems(): List<ModeItemModel> {
        return items
    }
}