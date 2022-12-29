package com.ganzory.wagba

import android.app.Application
import com.ganzory.wagba.shared.AppDatabase
import com.ganzory.wagba.ui.cart.CartRepository
import com.ganzory.wagba.ui.orders.OrdersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WagbaApplication : Application() {
    private val localDatabase: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val remoteDatabase by lazy { Firebase.database.reference }
    val cartRepository: CartRepository by lazy { CartRepository(localDatabase.cartItemDao()) }
    val ordersRepository: OrdersRepository by lazy {
        OrdersRepository(
            remoteDatabase,
            FirebaseAuth.getInstance().currentUser?.uid ?: "-1",
        )
    }
}