package basic.training.compose.domain.repository

import basic.training.compose.domain.model.User

interface UserRepository {
    suspend fun searchUsers(query: String, page: Int, perPage: Int) : List<User>
}