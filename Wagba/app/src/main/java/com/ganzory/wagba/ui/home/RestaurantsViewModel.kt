package com.ganzory.wagba.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class RestaurantsViewModel : ViewModel() {
    private val database = Firebase.database.reference
    private val restaurantsReference: DatabaseReference = database.child("restaurants")
    private var restaurantsListener: ValueEventListener? = null
    private val _restaurants = MutableLiveData<List<RestaurantModel>>().apply {
        value = listOf()
    }
    val restaurants: LiveData<List<RestaurantModel>> = _restaurants

    fun listenToRestaurants() {
        if (restaurantsListener != null) {
            return
        }
        restaurantsListener = restaurantsReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.w(TAG, "onDataChange $dataSnapshot")
                    _restaurants.value =
                        dataSnapshot.children.mapNotNull { it.getValue<RestaurantModel>() }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "RestaurantsViewModel:onCancelled", databaseError.toException())
                }
            },
        )
    }

    fun unlistenToRestaurants() {
        if (restaurantsListener != null) {
            restaurantsReference.removeEventListener(restaurantsListener!!)
        }
    }

    companion object {
        private const val TAG = "RestaurantsViewModel"
    }
}