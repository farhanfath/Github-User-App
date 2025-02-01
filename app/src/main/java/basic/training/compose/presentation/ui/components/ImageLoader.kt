package basic.training.compose.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ImageLoader(modifier: Modifier = Modifier, url : String) {
    val painter = rememberAsyncImagePainter(model = url)

    Image(
        painter = painter,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        contentDescription = "Profile Picture Image",
        contentScale = ContentScale.Crop
    )
}