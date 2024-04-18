package com.vivekchoudhary.imageloadingapp.view.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vivekchoudhary.imageloader.ImageRequest
import com.vivekchoudhary.imageloadingapp.R
import com.vivekchoudhary.imageloadingapp.common.DataWrapper
import com.vivekchoudhary.imageloadingapp.repository.model.Photo
import com.vivekchoudhary.imageloadingapp.viewmodel.PhotosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ListScreen(viewModel: PhotosViewModel, errorResId: Int, placeholderResId: Int) {
    val state = viewModel.photosStateFlow.collectAsState().value
    when (state) {
        is DataWrapper.Loading -> Loader()
        is DataWrapper.Failure -> ErrorMessage(throwable = state.cause!!)
        is DataWrapper.Success -> {
            LazyList(list = state.data!!, errorResId, placeholderResId)
        }
    }
}

@Composable
fun LazyList(list: List<Photo>, errorResId: Int, placeholderResId: Int) {
    val coroutineScope = rememberCoroutineScope()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(items = list, key = {
            it.id
        }) { photo ->
            val imageUrl = photo.urls.regularUrl
            val context = LocalContext.current
            var bitmap by remember { mutableStateOf<Bitmap?>(null) }
            LaunchedEffect(imageUrl) {
                coroutineScope.launch {
                    bitmap = withContext(Dispatchers.IO) {
                        ImageRequest.Builder(context)
                            .data(imageUrl)
                            .error(errorResId)
                            .placeholder(placeholderResId)
                            .build()
                    }
                }
            }

            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Image(
                    painter = painterResource(id = placeholderResId),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.Gray)
                        .width(dimensionResource(id = R.dimen.placeholder_width))
                        .height(dimensionResource(id = R.dimen.placeholder_height))
                )
            }

        }

    }
}