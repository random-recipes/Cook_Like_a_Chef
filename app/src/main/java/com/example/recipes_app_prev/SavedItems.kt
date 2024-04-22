package com.example.recipes_app_prev

object SavedItems {
    private val items = mutableListOf<List<String>>()


    fun addItem(item: List<String>) {
        if (!items.contains(item)) {
            items.add(item)
        }
    }

    fun deleteItem(item: List<String>) {
        items.remove(item)
    }

    fun getItems(): MutableList<List<String>> {
        return items
    }

}
