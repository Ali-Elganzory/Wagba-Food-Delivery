package com.ganzory.wagba.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ganzory.wagba.databinding.CartItemCardBinding

class CartAdapter(
    private val onRemove: (CartItem) -> Unit,
    private val onIncrement: (CartItem) -> Unit,
    private val onDecrement: (CartItem) -> Unit,
) : ListAdapter<CartItem, CartAdapter.CartItemViewHolder>(ItemsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            CartItemCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CartItemViewHolder(private val binding: CartItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.apply {
                tvName.text = item.name
                tvDesc.text = item.description
                tvPrice.text = item.price.toString()
                tvQuantity.text = item.quantity.toString()
                imageUrl = item.imageUrl
                btnRemove.setOnClickListener { onRemove(item) }
                btnIncrement.setOnClickListener { onIncrement(item) }
                btnDecrement.setOnClickListener { onDecrement(item) }
            }
        }
    }

    class ItemsComparator : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.restaurantId == newItem.restaurantId && oldItem.dishId == newItem.dishId
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}