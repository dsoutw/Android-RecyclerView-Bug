/**
 * Copyright 2023 Dyi-Shing Ou
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

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
            binding.itemText.text = item.text
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