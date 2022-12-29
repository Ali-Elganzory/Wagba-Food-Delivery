package com.ganzory.wagba.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ganzory.wagba.databinding.RestaurantCardBinding

class RestaurantsAdapter(
    private val onClickListener: (RestaurantModel) -> Unit
) : ListAdapter<RestaurantModel, RestaurantsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RestaurantCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
    }

    inner class ViewHolder(private val binding: RestaurantCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RestaurantModel) {
            binding.root.setOnClickListener { onClickListener(model) }
            binding.restaurant = model
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RestaurantModel>() {
        override fun areItemsTheSame(oldItem: RestaurantModel, newItem: RestaurantModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RestaurantModel,
            newItem: RestaurantModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}