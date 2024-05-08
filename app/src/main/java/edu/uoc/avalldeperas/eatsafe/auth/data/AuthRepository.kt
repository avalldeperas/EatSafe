package edu.uoc.avalldeperas.eatsafe.auth.data

import edu.uoc.avalldeperas.eatsafe.auth.domain.model.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): String
    suspend fun signUp(email: String, password: String): String
    suspend fun passwordReset(email: String): String
    suspend fun updateProfile(displayName: String): Boolean
    fun signOut(): Boolean

    fun getCurrentUser(): User
}