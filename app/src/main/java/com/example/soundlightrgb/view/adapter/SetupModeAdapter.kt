package com.example.soundlightrgb.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soundlightrgb.databinding.ModeSetupItemBinding
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.viewHolder.SetupModeItemViewHolder

class SetupModeAdapter(private var items: MutableList<ModeItemModel>): RecyclerView.Adapter<SetupModeItemViewHolder>() {
    private lateinit var recycler: RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetupModeItemViewHolder {
        val binding = ModeSetupItemBinding.inflate(LayoutInflater.from(parent.context))
        return SetupModeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SetupModeItemViewHolder, position: Int) {
        holder.bind(items[position], { deleteLastMode() }, { index, name ->
            onNameChanged(index, name)
        })
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recycler = recyclerView
    }

    fun addMode() {
        if (items.lastOrNull()?.name  != "") {
            val modeItem = ModeItemModel(items.count().toString(), "asdasd", true)
            items.forEach { it.delete = false }
            items.add(modeItem)
            fixHeight()
            notifyItemInserted(items.count() - 1)
        }
    }

    fun deleteLastMode() {
        items.removeLast()
        items.lastOrNull()?.delete = true
        fixHeight()
        notifyItemRemoved(items.count())
    }

    private fun onNameChanged(index: String, name: String) {
        items.find { it.index == index }?.name  = name
    }

    private fun fixHeight() {
        recycler.layoutParams.height = items.count() * HEIGHT_BASE
    }

    fun setItems(items: List<ModeItemModel>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyItemRangeInserted(0, itemCount)
    }

    override fun getItemCount(): Int = items.count()

    companion object {
        private val HEIGHT_BASE = 150
    }
}