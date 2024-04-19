# ComposeImageVerticalGridList Application

Create a simple Android application that fetches images from a public API endpoint and displays it in a vertical grid list format. The image loading is done without using any external libraries.

## Demo Video

https://github.com/cvivek07/ComposeImageGridList/assets/16047933/fc580571-842d-46c8-9b1c-03e1fa7fdb95


## Libraries and Architecture Used

1. MVVM Architecture.
2. Dependency Injection using Hilt.
3. Jetpack compose based user interface.
4. Latest navigation controller for navigation between screens.
5. Supports latest android 14.
6. Dependency management using version catalog.
7. ImageLoader implementation using memory and disk cache.
8. Utilized coroutines whereever applicable.
9. Used latest android studio Iguana for development.

## Instructions

1. clone the repository.
2. Run the app to see the image list.

## Usage

## Usage

```kotlin
@Composable
fun LazyList(list: List<Photo>, errorResId: Int, placeholderResId: Int) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(items = list,
            key = {
                it.id
            }
        ) { photo ->
            val imageUrl = photo.getImageUrl()
            val context = LocalContext.current
            var bitmap by remember { mutableStateOf<Bitmap?>(null) }
            LaunchedEffect(imageUrl) {
                bitmap = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .error(errorResId)
                    .placeholder(placeholderResId)
                    .build()
            }
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(photo.getAspectRatio().toFloat())
                )
            } else {
                Image(
                    painter = painterResource(id = placeholderResId),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.Gray)
                        .aspectRatio(
                            photo
                                .getAspectRatio()
                                .toFloat()
                        )
                )
            }
        }
    }
}
```

