package edu.uoc.avalldeperas.eatsafe.auth.login.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.d("avb", "signIn failure: ${e.message}")
            false
        }
    }

    override suspend fun signUp(email: String, password: String): String {
        return try {
            val response = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            response.user!!.uid
        } catch (e: Exception) {
            Log.d("avb", "signUp failure: ${e.message}")
            ""
        }
    }

    override suspend fun passwordReset(email: String): Boolean {
        return try {
            Firebase.auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            Log.d("avb", "password reset failure: ${e.message}")
            false
        }
    }

    override fun signOut(): Boolean {
        return try {
            Firebase.auth.signOut()
            true
        } catch (e: Exception) {
            Log.d("avb", "logout failure: ${e.message}")
            false
        }
    }

    override fun getCurrentUser(): FirebaseUser {
        return Firebase.auth.currentUser!!
    }

    override suspend fun updateProfile(displayName: String): Boolean {
        val currentUser = Firebase.auth.currentUser!!
        val request = buildUpdateProfileRequest(displayName)

        return try {
            currentUser.updateProfile(request).await()
            Firebase.auth.updateCurrentUser(currentUser)
            true
        } catch (e: Exception) {
            Log.d("avb", "logout failure: ${e.message}")
            false
        }
    }

    private fun buildUpdateProfileRequest(displayName: String): UserProfileChangeRequest {
        return UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
    }
}