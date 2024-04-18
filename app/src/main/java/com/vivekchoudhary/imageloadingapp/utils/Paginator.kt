package com.vivekchoudhary.imageloadingapp.utils

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}