package com.ganzory.wagba.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganzory.wagba.databinding.FragmentCartBinding
import com.ganzory.wagba.WagbaApplication
import com.ganzory.wagba.shared.MarginItemDecoration
import com.ganzory.wagba.ui.orders.OrderModel
import com.ganzory.wagba.ui.orders.OrderStatus
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime

class CartFragment : Fragment() {
    private val viewModel: CartViewModel by activityViewModels {
        CartViewModelFactory(
            (activity!!.application as WagbaApplication).cartRepository,
            (activity!!.application as WagbaApplication).ordersRepository
        )
    }

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        val adapter = CartAdapter(
            onRemove = {
                viewModel.removeFromCart(it)
                Toast.makeText(context, "Removed ${it.name} from cart", Toast.LENGTH_SHORT).show()
            },
            onIncrement = { viewModel.updateQuantity(it, it.quantity + 1) },
            onDecrement = {
                if (it.quantity > 1) {
                    viewModel.updateQuantity(it, it.quantity - 1)
                } else {
                    Toast.makeText(context, "Quantity cannot be less than 1", Toast.LENGTH_SHORT)
                        .show()
                }
            },
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(MarginItemDecoration(24))
            this.adapter = adapter
        }
        viewModel.items.observe(activity!!) { items ->
            items.let { adapter.submitList(it) }
            binding.tvTotalValue.text = " " + items.sumOf { it.price }.toString()
        }

        binding.btnOrder.setOnClickListener {
            val gateId = binding.rgGate.checkedRadioButtonId
            val timeId = binding.rgTime.checkedRadioButtonId
            val now = LocalDateTime.now()

            if (viewModel.items.value?.isEmpty() != false) {
                Toast.makeText(context, "Cart is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (timeId == -1 || gateId == -1) {
                Toast.makeText(context, "Please select a time and gate", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val gate = when (gateId) {
                binding.rbGate1.id -> "3"
                binding.rbGate2.id -> "4"
                else -> "No gate"
            }
            val time = when (timeId) {
                binding.rbTime1.id -> "12:00 PM"
                binding.rbTime2.id -> "03:00 PM"
                else -> "No time"
            }
            if (gate == "No gate" || time == "No time") {
                Toast.makeText(context, "Please select a time and gate", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (gateId == binding.rbGate1.id && now.isAfter(now.withHour(10)) && now.isBefore(
                    now.withHour(12)
                )
            ) {
                Toast.makeText(
                    context, "For this time you should order before 10", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (gateId == binding.rbGate2.id && now.isAfter(now.withHour(13)) && now.isBefore(
                    now.withHour(15)
                )
            ) {
                Toast.makeText(
                    context, "For this time you should order before 1", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val order = OrderModel(
                id = "-1",
                items = viewModel.items.value?.size ?: 0,
                gate = gate,
                time = time,
                date = now.toString(),
                total = viewModel.items.value?.sumOf { it.price } ?: 0.0,
                status = OrderStatus.Paid,
            )
            viewModel.addOrder(order)
            Snackbar.make(binding.root, "Order placed", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
}