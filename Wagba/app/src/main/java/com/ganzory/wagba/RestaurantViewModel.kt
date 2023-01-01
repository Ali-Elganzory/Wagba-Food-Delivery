package com.ganzory.wagba

import android.util.Log
import androidx.lifecycle.*
import com.ganzory.wagba.ui.cart.CartItem
import com.ganzory.wagba.ui.cart.CartRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class RestaurantViewModel(
    private val uid: String,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val database = Firebase.database.reference
    private var restaurantId: String = "-1"
    private lateinit var dishesReference: DatabaseReference
    private var dishesListener: ValueEventListener? = null
    private val _dishes = MutableLiveData<List<DishModel>>().apply {
        value = listOf()
    }
    val dishes: LiveData<List<DishModel>> = _dishes

    fun addToCart(dish: DishModel, restaurantId: String, onFinish: (q: Int) -> Unit) =
        viewModelScope.launch {
            cartRepository.getItem(uid, restaurantId, dish.id).let {
                if (it == null) {
                    cartRepository.insert(
                        CartItem(
                            uid = uid,
                            restaurantId = restaurantId,
                            dishId = dish.id,
                            name = dish.name,
                            description = dish.description,
                            imageUrl = dish.imageUrl,
                            unitPrice = dish.price,
                            quantity = 1
                        )
                    )
                    onFinish(1)
                } else {
                    cartRepository.update(it.copy(quantity = it.quantity + 1))
                    onFinish(it.quantity + 1)
                }
            }
        }

    fun listenToDishes(restaurantId: String) {
        if (restaurantId == this.restaurantId) {
            return
        }
        this.restaurantId = restaurantId
        dishesReference = database.child("dishes").child(restaurantId)
        dishesListener = dishesReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    _dishes.value = dataSnapshot.children.mapNotNull { it.getValue<DishModel>() }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(TAG, "RestaurantViewModel:onCancelled", databaseError.toException())
                }
            },
        )
    }

    fun unlistenToDishes() {
        restaurantId = "-1"
        if (dishesListener != null) {
            dishesReference.removeEventListener(dishesListener!!)
        }
    }

    companion object {
        private const val TAG = "RestaurantViewModel"
    }
}

class RestaurantViewModelFactory(
    private val uid: String,
    private val repository: CartRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantViewModel(uid, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}