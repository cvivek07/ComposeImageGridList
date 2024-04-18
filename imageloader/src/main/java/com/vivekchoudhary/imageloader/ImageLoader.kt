package com.vivekchoudhary.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.vivekchoudhary.imageloader.diskcache.DiskCache
import com.vivekchoudhary.imageloader.diskcache.DiskLruCache
import com.vivekchoudhary.imageloader.memorycache.MemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


class ImageRequest private constructor(
    private val context: Context,
    private val url: String,
    private val errorResId: Int,
    private val placeholderResId: Int,
    private val imageCache: ICache
) {
    suspend fun fetchBitmap(): Bitmap {
        val cachedBitmap = getImageFromCache()
        if (cachedBitmap != null) {
            return cachedBitmap
        }


        return withContext(Dispatchers.IO) {
            downloadImage(url)?.also {
                imageCache.put(url, it)
            } ?: getErrorDrawable()
        }
    }

    private suspend fun getImageFromCache() : Bitmap? {
        return withContext(Dispatchers.IO) {
            imageCache.get(url)
        }
    }

    private fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = AppCompatResources.getDrawable(context, placeholderResId)!!.toBitmap()
        try {
            val conn: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            bitmap = BitmapFactory.decodeStream(conn.inputStream)
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    private fun getErrorDrawable(): Bitmap {
        val drawable = AppCompatResources.getDrawable(context, errorResId)
        return drawable!!.toBitmap()
    }

    class Builder(private val context: Context) {
        private lateinit var url: String
        private var errorResId: Int = -1
        private var placeholderResId: Int = -1
        private var imageCache: ICache = DiskCache(context)

        fun data(url: String): Builder {
            this.url = url
            return this
        }

        fun error(@DrawableRes errorResId: Int): Builder {
            this.errorResId = errorResId
            return this
        }

        fun placeholder(@DrawableRes placeholderResId: Int): Builder {
            this.placeholderResId = placeholderResId
            return this
        }

        fun cache(imageCache: ICache): Builder {
            this.imageCache = imageCache
            return this
        }

        suspend fun build(): Bitmap {
            require(::url.isInitialized) { "URL must be set" }
            require(errorResId != -1) { "Error resource ID must be set" }
            return ImageRequest(context, url, errorResId, placeholderResId, imageCache).fetchBitmap()
        }
    }
}