package com.vivekchoudhary.imageloader.diskcache

import android.content.Context
import android.graphics.Bitmap
import com.vivekchoudhary.imageloader.ICache
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DiskCache(context: Context) : ICache {

    private var cache: DiskLruCache = DiskLruCache(context.cacheDir,  10 * 1024 * 1024)

    override fun get(url: String): Bitmap? {
        val key = md5(url).toString()
        return cache.get(key)
    }

    override fun put(url: String, bitmap: Bitmap) {
        val key: String = md5(url).toString()
        cache.put(key, bitmap)
    }

    override fun clear() {
        cache.clear()
    }

    private fun md5(url: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(url.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}