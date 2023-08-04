package com.example.infrastructure.adapter.output

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserNotFound
import com.example.application.domain.UserStatus.ACTIVE
import com.example.application.domain.UserStatus.DEACTIVATED
import com.example.application.port.output.UserRepository

class InMemoryUsers(users: List<User>? = null) : UserRepository {

    private val users: List<User> = users ?: listOf(
        User(UserId(USER_ID_1), Name("Elisa"), ACTIVE),
        User(UserId(USER_ID_2), Name("Carlos"), ACTIVE),
        User(UserId(USER_ID_3), Name("Roberta"), DEACTIVATED)
    )

    override fun findAll(): List<User> = users

    override fun find(userId: UserId): Either<UserNotFound, User> =
        users.find { it.id == userId }?.right()
            ?: UserNotFound.left()
}
