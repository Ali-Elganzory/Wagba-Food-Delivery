package com.ganzory.wagba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ganzory.wagba.databinding.DishCardBinding

class DishesAdapter(
    private val onClickListener: (DishModel) -> Unit
) : ListAdapter<DishModel, DishesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DishCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
    }

    inner class ViewHolder(private val binding: DishCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DishModel) {
            binding.model = model
            binding.btnAddToCart.setOnClickListener { onClickListener(model) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DishModel>() {
        override fun areItemsTheSame(oldItem: DishModel, newItem: DishModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DishModel,
            newItem: DishModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}