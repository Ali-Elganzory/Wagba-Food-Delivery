package com.ganzory.wagba

data class DishModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Double = -1.0,
    val available: Boolean = false,
)