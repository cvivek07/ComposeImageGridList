package com.vivekchoudhary.imageloadingapp.di

import com.vivekchoudhary.imageloadingapp.common.DefaultDispatcherProvider
import com.vivekchoudhary.imageloadingapp.common.DispatcherProvider
import com.vivekchoudhary.imageloadingapp.repository.PhotosService
import com.vivekchoudhary.imageloadingapp.repository.PhotosRepository
import com.vivekchoudhary.imageloadingapp.repository.PhotosRepositoryImpl
import com.vivekchoudhary.imageloadingapp.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dependency Injection using Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindsDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider

    @Binds
    abstract fun providePostsRepository(postsRepository: PhotosRepositoryImpl): PhotosRepository

    companion object {
        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
                .build()
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("client_id", "kz3dm3FCIsye4zrPyc6IDmzwaIHISv3jLqSzpuKJVhU")
                .build()
            request.url(newUrl)
            return chain.proceed(request.build())
        }

        @Provides
        fun providesPostServiceApi(retrofit: Retrofit): PhotosService {
            return retrofit.create(PhotosService::class.java)
        }
    }
}