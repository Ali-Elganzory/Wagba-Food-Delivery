package com.ganzory.wagba

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganzory.wagba.databinding.ActivityRestaurantBinding
import com.ganzory.wagba.shared.MarginItemDecoration
import com.ganzory.wagba.ui.home.RestaurantsFragment
import com.google.android.material.snackbar.Snackbar


class RestaurantActivity : AppCompatActivity() {
    private var _binding: ActivityRestaurantBinding? = null
    private val viewModel: RestaurantViewModel by viewModels() {
        RestaurantViewModelFactory((application as WagbaApplication).cartRepository)
    }
    private val binding get() = _binding!!
    private lateinit var id: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.apply {
            id = getString(RestaurantsFragment.RESTAURANT_ID_KEY)!!
            name = getString(RestaurantsFragment.RESTAURANT_NAME_KEY)!!
        }

        binding.restaurantToolbar.title = name

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.listenToDishes(id)
        binding.rvDishes.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(46))
            adapter = DishesAdapter { dish ->
                viewModel.addToCart(
                    dish = dish, restaurantId = this@RestaurantActivity.id
                )
                {
                    Snackbar.make(binding.root, "$it ${dish.name}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.unlistenToDishes()
    }
}