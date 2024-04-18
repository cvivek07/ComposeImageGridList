package com.vivekchoudhary.imageloader

import android.graphics.Bitmap

interface ICache {
    fun put(url: String, bitmap: Bitmap)
    fun get(url: String): Bitmap?
    fun clear()
}