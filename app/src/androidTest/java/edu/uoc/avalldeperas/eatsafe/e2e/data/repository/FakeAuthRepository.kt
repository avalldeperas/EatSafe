package edu.uoc.avalldeperas.eatsafe.e2e.data.repository

import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_EMAIL
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_VALID_PASSWORD
import javax.inject.Inject


class FakeAuthRepository @Inject constructor() : AuthRepository {

    private val users = listOf(
        User(email = TESTER_EMAIL)
    )

    override suspend fun signIn(email: String, password: String): String {
        val user = users.filter { user -> email == user.email && password == TESTER_VALID_PASSWORD }
        return if (user.isEmpty()) "Invalid credentials" else ""
    }

    override suspend fun signUp(email: String, password: String): String {
        return "success"
    }

    override suspend fun passwordReset(email: String): String {
        return "success"
    }

    override suspend fun updateProfile(displayName: String): Boolean {
        users[0].displayName = displayName
        return true
    }

    override fun signOut(): Boolean {
        return true
    }

    override fun getCurrentUser(): User {
        return users[0]
    }
}