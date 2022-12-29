package com.ganzory.wagba.ui.orders

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrdersRepository(
    databaseReference: DatabaseReference,
    userId: String
) {
    private val ordersReference = databaseReference.root.child("orders").child(userId)

    private var ordersFlow = callbackFlow {
        val listener = ordersReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.mapNotNull { it.getValue<OrderModel>() }.let {
                        trySend(it)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "OrdersRepository:onCancelled", databaseError.toException())
                }
            },
        )
        awaitClose { ordersReference.removeEventListener(listener) }
    }


    fun getAll(): Flow<List<OrderModel>> = ordersFlow

    fun addOrder(order: OrderModel) {
        val ref = ordersReference.push()
        ref.setValue(order.copy(id = ref.key ?: "-1"))
    }

    companion object {
        private const val TAG = "RestaurantsViewModel"
    }
}