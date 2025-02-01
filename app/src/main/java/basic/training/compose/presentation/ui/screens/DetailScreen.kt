package basic.training.compose.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(username: String) {
    Scaffold { paddingValues ->
        Text(
            modifier = Modifier.padding(paddingValues),
            text = username
        )

    }
}