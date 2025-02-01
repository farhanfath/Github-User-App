package basic.training.compose.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object Profile

@Serializable
data class Detail(
    val username: String,
    val type: String,
    val avatarUrl: String
)