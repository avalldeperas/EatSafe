package edu.uoc.avalldeperas.eatsafe.e2e.data.repository

import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.auth.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.User
import edu.uoc.avalldeperas.eatsafe.e2e.common.TestConstants.TESTER_EMAIL
import edu.uoc.avalldeperas.eatsafe.e2e.common.TestConstants.TESTER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import javax.inject.Inject

class FakeUserRepository @Inject constructor() : UsersRepository {

    override val user: User
        get() = User(
            uid = TESTER_ID,
            currentCity = "El Poblenou, Sant Martí, Barcelona",
            dateJoined = Timestamp(SimpleDateFormat("dd/M/yyyy").parse("30/4/2024")!!),
            email = TESTER_EMAIL,
            latitude = 41.4033546,
            longitude = 2.2028843,
            geohash = "sp3ed1k04b",
            intolerances = mutableListOf("Gluten", "Lactose")
        )

    override fun getUser(userId: String): Flow<User?> {
        return flow {
            emit(user.copy(uid = userId))
        }
    }

    override suspend fun save(user: User): Boolean {
        return true
    }

    override suspend fun update(user: User): Boolean {
        return true
    }
}