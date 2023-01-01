package com.ganzory.wagba.ui.orders

import androidx.lifecycle.*

class OrdersViewModel(
    uid: String,
    ordersRepository: OrdersRepository,
) : ViewModel() {
    val items: LiveData<List<OrderModel>> = ordersRepository.getAll(uid).asLiveData()
}

class OrdersViewModelFactory(
    private val uid: String,
    private val ordersRepository: OrdersRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrdersViewModel(uid, ordersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}