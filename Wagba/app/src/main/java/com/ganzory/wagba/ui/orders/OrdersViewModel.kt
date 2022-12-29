package com.ganzory.wagba.ui.orders

import androidx.lifecycle.*

class OrdersViewModel(
    private val ordersRepository: OrdersRepository,
) : ViewModel() {
    val items: LiveData<List<OrderModel>> = ordersRepository.getAll().asLiveData()
}

class OrdersViewModelFactory(
    private val ordersRepository: OrdersRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrdersViewModel(ordersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}