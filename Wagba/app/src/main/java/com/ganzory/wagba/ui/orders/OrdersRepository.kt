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
    private val databaseReference: DatabaseReference,
) {
    private fun getUserOrdersReference(uid: String) = databaseReference.child("orders").child(uid)

    fun getAll(uid: String): Flow<List<OrderModel>> = callbackFlow {
        val reference = getUserOrdersReference(uid)
        val listener = reference.addValueEventListener(
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
        awaitClose { reference.removeEventListener(listener) }
    }

    fun addOrder(uid: String, order: OrderModel) {
        val reference = getUserOrdersReference(uid).push()
        reference.setValue(order.copy(id = reference.key ?: "-1"))
    }

    companion object {
        private const val TAG = "RestaurantsViewModel"
    }
}