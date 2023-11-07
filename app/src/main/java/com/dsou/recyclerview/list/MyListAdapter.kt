package com.dsou.recyclerview.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dsou.recyclerview.databinding.HolderItemBinding

class MyListAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<ItemData, MyListAdapter.Holder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = createHolderBinding(parent)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        Log.d("RuleSelectionListAdapter", "onBindViewHolder item=${item.id} position=$position")
        holder.onBind(item)
    }

    override fun onViewRecycled(holder: Holder) {
        holder.onUnbind()
        super.onViewRecycled(holder)
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: ItemData)
    }

    inner class Holder internal constructor(
        private val binding: HolderItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ItemData) {
            binding.item = item
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }

        fun onUnbind() {
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ItemData>() {
        override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        @JvmStatic
        private fun createHolderBinding(parent: ViewGroup): HolderItemBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return HolderItemBinding.inflate(layoutInflater, parent, false)
        }
    }
}