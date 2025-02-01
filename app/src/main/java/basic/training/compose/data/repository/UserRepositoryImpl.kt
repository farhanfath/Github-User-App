package basic.training.compose.data.repository

import basic.training.compose.data.model.UserData
import basic.training.compose.data.remote.GitHubApi
import basic.training.compose.domain.model.User
import basic.training.compose.domain.repository.UserRepository

class UserRepositoryImpl(private val api : GitHubApi) : UserRepository {
    override suspend fun searchUsers(query: String, page: Int, perPage: Int): List<User> {
        return api.getListUsers(query, page, perPage).items.map { it.toDomain() }
    }
}

fun UserData.toDomain(): User {
    return User(login = login, avatarUrl = avatarUrl, htmlUrl = htmlUrl, type = type)
}