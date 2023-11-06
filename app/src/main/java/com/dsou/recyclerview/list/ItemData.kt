package com.dsou.recyclerview.list

data class ItemData(
    val id: Int
) {
    val text: String get() = id.toString()
}
