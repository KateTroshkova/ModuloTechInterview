package com.noveogroup.modulotechinterview.common.android.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("NotifyDataSetChanged")
abstract class BaseRecyclerAdapter<Model, ViewHolder : RecyclerView.ViewHolder>(
    hasStableIds: Boolean = false
) : RecyclerView.Adapter<ViewHolder>() {

    protected val items = mutableListOf<Model>()

    init {
        setHasStableIds(hasStableIds)
    }

    final override fun setHasStableIds(hasStableIds: Boolean) = super.setHasStableIds(hasStableIds)

    fun isEmpty() = items.isEmpty()

    override fun getItemCount() = items.size

    open fun getItem(position: Int) = items[position]

    open fun addItems(newItems: List<Model>, notify: Boolean = true) {
        if (newItems.isNotEmpty()) items.addAll(newItems)
        if (notify) notifyDataSetChanged()
    }

    open fun setItems(items: List<Model>, notify: Boolean = true) {
        clear(false)
        addItems(items, notify)
    }

    open fun removeItem(position: Int, notify: Boolean = true) = items.removeAt(position).also {
        if (notify) notifyItemRemoved(position)
    }

    open fun clear(notify: Boolean = true) {
        items.clear()
        if (notify) notifyDataSetChanged()
    }
}
