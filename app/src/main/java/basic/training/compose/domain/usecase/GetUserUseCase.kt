package basic.training.compose.domain.usecase

import basic.training.compose.domain.model.User
import basic.training.compose.domain.repository.UserRepository

class GetUsersUseCase(private val repository: UserRepository) {
    suspend fun execute(query: String, page: Int, perPage: Int): List<User> {
        return repository.searchUsers(query, page, perPage)
    }
}