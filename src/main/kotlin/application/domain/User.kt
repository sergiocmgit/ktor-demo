package com.example.application.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.application.domain.UserStatus.ACTIVE

@JvmInline
value class UserId(val value: String)

@JvmInline
value class Name(val value: String)

enum class UserStatus {
    ACTIVE,
    DEACTIVATED
}

data class User(
    val id: UserId,
    val name: Name,
    val status: UserStatus,
) {

    fun checkIsActive(): Either<UserInvalidStatus, User> =
        if (status == ACTIVE) right()
        else UserInvalidStatus.left()
}