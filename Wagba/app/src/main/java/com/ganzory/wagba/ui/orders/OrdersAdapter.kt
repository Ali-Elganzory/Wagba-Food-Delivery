package com.ganzory.wagba.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ganzory.wagba.databinding.OrderCardBinding

class OrdersAdapter : ListAdapter<OrderModel, OrdersAdapter.OrderViewHolder>(ItemsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            OrderCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class OrderViewHolder(private val binding: OrderCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderModel) {
            binding.apply {
                binding.tvNumber.text = item.id
                binding.tvItems.text = item.items.toString()
                binding.tvStatus.text = item.status.status
                binding.tvTotal.text = item.total.toString()
                binding.tvGate.text = item.gate
                binding.tvTime.text = item.time
            }
        }
    }

    class ItemsComparator : DiffUtil.ItemCallback<OrderModel>() {
        override fun areItemsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
            return oldItem == newItem
        }
    }
}