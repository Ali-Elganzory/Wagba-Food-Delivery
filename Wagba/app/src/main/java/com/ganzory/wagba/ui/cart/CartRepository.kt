package com.ganzory.wagba.ui.cart

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val cartItemDao: CartItemDao
) {
    private var cartItemsFlow: Flow<List<CartItem>> = cartItemDao.getAll()

    fun getAll(): Flow<List<CartItem>> = cartItemsFlow

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() = cartItemDao.deleteAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getByRestaurantAndDishIds(restaurantId: String, dishId: String) =
        cartItemDao.getByRestaurantAndDishIds(restaurantId, dishId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(cartItem: CartItem) = cartItemDao.update(cartItem)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cartItem: CartItem) = cartItemDao.insert(cartItem)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(cartItem: CartItem) = cartItemDao.delete(cartItem)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() = cartItemDao.deleteAll()
}