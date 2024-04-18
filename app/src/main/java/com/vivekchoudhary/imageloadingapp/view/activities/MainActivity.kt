package com.vivekchoudhary.imageloadingapp.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vivekchoudhary.imageloadingapp.R
import com.vivekchoudhary.imageloadingapp.ui.theme.ListAppTheme
import com.vivekchoudhary.imageloadingapp.view.composables.ListScreen
import com.vivekchoudhary.imageloadingapp.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListScreen(viewModel, R.drawable.error_image, R.drawable.image_placeholder)
                }
            }
        }
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModel.fetchPhotos()
    }
}