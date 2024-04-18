package com.vivekchoudhary.imageloadingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivekchoudhary.imageloadingapp.common.DataWrapper
import com.vivekchoudhary.imageloadingapp.repository.PhotosRepository
import com.vivekchoudhary.imageloadingapp.repository.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: PhotosRepository
) : ViewModel() {

    private val _photosMutableStateFlow: MutableStateFlow<DataWrapper<List<Photo>>> =
        MutableStateFlow(DataWrapper.Loading())
    val photosStateFlow: StateFlow<DataWrapper<List<Photo>>> get() = _photosMutableStateFlow

    fun fetchPhotos() {
        viewModelScope.launch {
            _photosMutableStateFlow.value = DataWrapper.Loading()
            val photos = repository.getPhotos()
            _photosMutableStateFlow.value = photos
        }
    }
}