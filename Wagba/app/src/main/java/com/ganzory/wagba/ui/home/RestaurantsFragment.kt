package com.ganzory.wagba.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganzory.wagba.RestaurantActivity
import com.ganzory.wagba.databinding.FragmentRestaurantsBinding
import com.ganzory.wagba.shared.MarginItemDecoration

class RestaurantsFragment : Fragment() {
    private var _binding: FragmentRestaurantsBinding? = null
    private val viewModel: RestaurantsViewModel by viewModels()
    private val binding get() = _binding!!

    companion object {
        internal const val RESTAURANT_ID_KEY: String = "id"
        internal const val RESTAURANT_NAME_KEY: String = "name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.listenToRestaurants()

        binding.rvRestaurants.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RestaurantsAdapter {
                val intent = Intent(
                    activity,
                    RestaurantActivity::class.java
                ).apply {
                    putExtra(RESTAURANT_ID_KEY, it.id)
                    putExtra(RESTAURANT_NAME_KEY, it.name)
                }
                activity?.startActivity(intent)
            }
            addItemDecoration(MarginItemDecoration(24))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.unlistenToRestaurants()
    }
}