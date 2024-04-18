package com.vivekchoudhary.imageloadingapp.repository

import com.vivekchoudhary.imageloadingapp.repository.model.Photo
import retrofit2.Response
import retrofit2.http.GET

interface PhotosService {

    @GET("photos?per_page=1000")
    suspend fun getPhotos(): Response<List<Photo>>
}