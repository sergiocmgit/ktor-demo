package fixtures.builders

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserStatus
import com.example.application.domain.UserStatus.ACTIVE
import com.example.infrastructure.adapter.input.UserDto
import kotlin.random.Random
import kotlin.random.nextInt

const val DEFAULT_USER_ID: String = "A"
const val DEFAULT_USER_NAME: String = "Eli"

fun randomUserId(): String = ('A' + Random.nextInt(0..25)).toString()

fun buildUser(
    userId: String = randomUserId(),
    status: UserStatus = ACTIVE,
) = User(
    UserId(userId),
    Name(DEFAULT_USER_NAME),
    status,
)

fun buildUserDto(userId: String = randomUserId()) =
    UserDto(
        userId,
        DEFAULT_USER_NAME,
        ACTIVE,
    )
