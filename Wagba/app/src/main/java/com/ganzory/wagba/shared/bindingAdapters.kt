package com.ganzory.wagba.shared

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ganzory.wagba.DishModel
import com.ganzory.wagba.DishesAdapter
import com.ganzory.wagba.R
import com.ganzory.wagba.ui.home.RestaurantModel
import com.ganzory.wagba.ui.home.RestaurantsAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
        }
    }
}

@BindingAdapter("restaurantsListData")
fun bindRestaurantsRecyclerView(
    recyclerView: RecyclerView, data: List<RestaurantModel>?
) {
    Log.w("bindRestaurantsRecyclerView", "New snapshot of restaurants:\n$data")
    val adapter = recyclerView.adapter as RestaurantsAdapter
    adapter.submitList(data)
}

@BindingAdapter("dishesListData")
fun bindDishesRecyclerView(
    recyclerView: RecyclerView, data: List<DishModel>?
) {
    Log.w("bindDishesRecyclerView", "New snapshot of dishes:\n$data")
    val adapter = recyclerView.adapter as DishesAdapter
    adapter.submitList(data)
}

@BindingAdapter("tvAvailability")
fun bindTextViewAvailability(view: TextView, value: Boolean?) {
    value?.let {
        if (value) {
            view.text = "Available"
            view.setTextColor(getColor(view.context, R.color.available))
        } else {
            view.text = "Unavailable"
            view.setTextColor(getColor(view.context, R.color.unavailable))
        }
    }
}

@BindingAdapter("tvPrice")
fun bindTextViewPrice(view: TextView, value: Double?) {
    value?.let {
        view.text = "$value EGP"
    }
}