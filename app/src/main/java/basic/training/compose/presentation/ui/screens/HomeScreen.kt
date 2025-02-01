package basic.training.compose.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import basic.training.compose.presentation.navigation.Detail
import basic.training.compose.presentation.ui.components.UserItem
import basic.training.compose.presentation.ui.theme.primaryLight
import basic.training.compose.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: UserViewModel = koinViewModel(), navController: NavController) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            delay(500L)
            viewModel.searchUsers(query = query, page = 1, perPage = 20)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "HomePage") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryLight, titleContentColor = Color.White)
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = primaryLight
                )
            }

            OutlinedTextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                },
                label = { Text(text = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
            )

            LazyColumn {
                items(users) { user ->
                    UserItem(user = user, onClick = { navController.navigate(Detail(username = user.login)) })
                }
            }
        }
    }
}