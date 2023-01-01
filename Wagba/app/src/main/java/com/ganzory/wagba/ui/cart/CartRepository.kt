package com.ganzory.wagba.ui.cart

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val cartItemDao: CartItemDao,
) {
    fun getAllByUid(uid: String): Flow<List<CartItem>> = cartItemDao.getAllByUid(uid)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() = cartItemDao.deleteAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getItem(uid: String, restaurantId: String, dishId: String) =
        cartItemDao.getItem(uid, restaurantId, dishId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(cartItem: CartItem) = cartItemDao.update(cartItem)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cartItem: CartItem) = cartItemDao.insert(cartItem)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(cartItem: CartItem) = cartItemDao.delete(cartItem)
}