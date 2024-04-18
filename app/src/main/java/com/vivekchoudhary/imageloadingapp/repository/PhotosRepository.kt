package com.vivekchoudhary.imageloadingapp.repository

import com.vivekchoudhary.imageloadingapp.common.DataWrapper
import com.vivekchoudhary.imageloadingapp.common.DispatcherProvider
import com.vivekchoudhary.imageloadingapp.repository.model.Photo
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PhotosRepository {
    suspend fun getPhotos(): DataWrapper<List<Photo>>
}

class PhotosRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: PhotosService
) : PhotosRepository {


    override suspend fun getPhotos(): DataWrapper<List<Photo>> {
        return withContext(dispatcherProvider.io()) {
            val response = apiService.getPhotos()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@withContext DataWrapper.Success(it)
                }
            }
            return@withContext DataWrapper.Failure(cause = ApiException("Something went wrong"))
        }
    }

}

class ApiException(message: String) : Exception(message)