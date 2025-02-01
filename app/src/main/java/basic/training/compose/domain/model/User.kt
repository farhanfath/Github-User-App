package basic.training.compose.domain.model

data class User(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val type: String
)
