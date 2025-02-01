package basic.training.compose.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import basic.training.compose.presentation.ui.components.ImageLoader
import coil3.compose.AsyncImage

@Composable
fun DetailScreen(
    username: String,
    type: String,
    avatarUrl: String
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(text = "Username: $username")

            Text(text = "User Type: $type")

            ImageLoader(url = avatarUrl)
        }
    }
}