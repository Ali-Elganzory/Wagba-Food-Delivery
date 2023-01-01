package com.ganzory.wagba.ui.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CART_ITEM_TABLE_NAME = "cart_item_table"
const val CART_ITEM_COLUMN_NAME_ID = "id"
const val CART_ITEM_COLUMN_NAME_RESTAURANT_ID = "restaurantId"
const val CART_ITEM_COLUMN_NAME_DISH_ID = "dishId"
const val CART_ITEM_COLUMN_NAME_UID = "uid"

@Entity(tableName = CART_ITEM_TABLE_NAME)
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String = "-1",
    val restaurantId: String = "-1",
    val dishId: String = "-1",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val unitPrice: Double = 0.0,
    val quantity: Int = 1,
) {
    val price: Double
        get() = unitPrice * quantity
}