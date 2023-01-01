package com.ganzory.wagba.ui.cart

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface CartItemDao {
    @Query("SELECT * FROM $CART_ITEM_TABLE_NAME WHERE uid = :uid ORDER BY $CART_ITEM_COLUMN_NAME_ID")
    fun getAllByUid(uid: String): Flow<List<CartItem>>

    @Query("SELECT * FROM $CART_ITEM_TABLE_NAME ORDER BY $CART_ITEM_COLUMN_NAME_ID")
    fun getAll(): Flow<List<CartItem>>

    @Query("SELECT * FROM $CART_ITEM_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: Int): CartItem?

    @Query(
        "SELECT * FROM $CART_ITEM_TABLE_NAME " +
                "WHERE $CART_ITEM_COLUMN_NAME_UID = :uid " +
                "AND $CART_ITEM_COLUMN_NAME_RESTAURANT_ID = :restaurantId " +
                "AND $CART_ITEM_COLUMN_NAME_DISH_ID = :dishId"
    )
    suspend fun getItem(uid: String, restaurantId: String, dishId: String): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM $CART_ITEM_TABLE_NAME")
    suspend fun deleteAll()
}