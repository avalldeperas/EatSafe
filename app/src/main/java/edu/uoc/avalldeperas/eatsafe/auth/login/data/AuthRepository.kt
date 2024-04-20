package edu.uoc.avalldeperas.eatsafe.auth.login.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): String
    suspend fun passwordReset(email: String): Boolean
    suspend fun updateProfile(displayName: String): Boolean
    fun signOut(): Boolean

    fun getCurrentUser(): FirebaseUser
}