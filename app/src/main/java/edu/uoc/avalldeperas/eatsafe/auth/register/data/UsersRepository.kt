package edu.uoc.avalldeperas.eatsafe.auth.register.data

import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User

interface UsersRepository {
    val user: User
    suspend fun getUser(userId: String): User?
    suspend fun save(user: User): Boolean
    suspend fun update(user: User): Boolean
}