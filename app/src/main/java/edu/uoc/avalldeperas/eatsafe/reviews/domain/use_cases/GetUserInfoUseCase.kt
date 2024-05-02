package edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
) {
    operator fun invoke(): Flow<User?> {
        val userId = authRepository.getCurrentUser().uid

        return usersRepository.getUser(userId)
    }
}