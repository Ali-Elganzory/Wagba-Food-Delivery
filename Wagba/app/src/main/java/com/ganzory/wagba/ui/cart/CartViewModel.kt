package com.ganzory.wagba.ui.cart

import androidx.lifecycle.*
import com.ganzory.wagba.ui.orders.OrderModel
import com.ganzory.wagba.ui.orders.OrdersRepository
import kotlinx.coroutines.launch

class CartViewModel(
    private val uid: String,
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
) : ViewModel() {
    val items: LiveData<List<CartItem>> = cartRepository.getAllByUid(uid).asLiveData()

    fun removeFromCart(cartItem: CartItem) = viewModelScope.launch {
        cartRepository.delete(cartItem)
    }

    fun updateQuantity(cartItem: CartItem, quantity: Int) = viewModelScope.launch {
        cartItem.copy(quantity = quantity).let {
            cartRepository.update(it)
        }
    }

    fun addOrder(order: OrderModel) = viewModelScope.launch {
        ordersRepository.addOrder(uid, order)
        cartRepository.clear()
    }
}

class CartViewModelFactory(
    private val uid: String,
    private val cartRepository: CartRepository,
    private val ordersRepository: OrdersRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(uid, cartRepository, ordersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}