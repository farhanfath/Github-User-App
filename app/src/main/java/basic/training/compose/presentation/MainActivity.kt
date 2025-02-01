package basic.training.compose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import basic.training.compose.presentation.navigation.NavHostApp
import basic.training.compose.presentation.ui.theme.GithubAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp {
                NavHostApp()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    GithubAppComposeTheme(dynamicColor = false, darkTheme = false) {
        content()
    }
}