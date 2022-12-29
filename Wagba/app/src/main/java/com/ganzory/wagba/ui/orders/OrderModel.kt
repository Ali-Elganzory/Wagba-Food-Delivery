package com.ganzory.wagba.ui.orders

enum class OrderStatus(val status: String) {
    Paid("Paid"),
    Preparing("Preparing"),
    Ready("Ready"),
    Delivered("Delivered"),
    Declined("Declined")
}

data class OrderModel(
    val id: String = "",
    val date: String = "",
    val items: Int = 0,
    val status: OrderStatus = OrderStatus.Preparing,
    val total: Double = 0.0,
    val gate: String = "",
    val time: String = "",
)