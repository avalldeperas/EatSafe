package edu.uoc.avalldeperas.eatsafe.auth.login.data

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signOut(): Boolean
    suspend fun passwordReset(email: String): Boolean
}