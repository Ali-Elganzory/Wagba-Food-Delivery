package com.ganzory.wagba.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganzory.wagba.WagbaApplication
import com.ganzory.wagba.databinding.FragmentOrdersBinding
import com.ganzory.wagba.shared.MarginItemDecoration
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OrdersFragment : Fragment() {
    private val viewModel: OrdersViewModel by activityViewModels {
        OrdersViewModelFactory(
            Firebase.auth.currentUser?.uid ?: "-1",
            (activity!!.application as WagbaApplication).ordersRepository
        )
    }

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val adapter = OrdersAdapter()
        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(MarginItemDecoration(24))
            this.adapter = adapter
        }
        viewModel.items.observe(activity!!) { items ->
            items.let { adapter.submitList(it) }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}