package edu.uoc.avalldeperas.eatsafe.auth.register.data

import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    val user: User
    fun getUser(userId: String): Flow<User?>
    suspend fun save(user: User): Boolean
    suspend fun update(user: User): Boolean
}