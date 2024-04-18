package com.vivekchoudhary.imageloader.diskcache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.vivekchoudhary.imageloader.ICache
import java.io.*

class DiskLruCache(private val cacheDir: File, maxSize: Int) : ICache {

    private val lruCache: LruCache<String, Bitmap> = LruCache(maxSize)

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    override fun put(key: String, bitmap: Bitmap) {
        synchronized(this) {
            if (get(key) == null) {
                val file = File(cacheDir, key.hashCode().toString())
                try {
                    val stream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            lruCache.put(key, bitmap)
        }
    }

    override fun get(key: String): Bitmap? {
        synchronized(this) {
            var bitmap = lruCache.get(key)
            if (bitmap == null) {
                val file = File(cacheDir, key.hashCode().toString())
                if (file.exists()) {
                    try {
                        val stream = FileInputStream(file)
                        bitmap = BitmapFactory.decodeStream(stream)
                        stream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (bitmap != null) {
                        lruCache.put(key, bitmap)
                    }
                }
            }
            return bitmap
        }
    }

    override fun clear() {
        synchronized(this) {
            val files = cacheDir.listFiles()
            if (files != null) {
                for (file in files) {
                    file.delete()
                }
            }
            lruCache.evictAll()
        }
    }

}
