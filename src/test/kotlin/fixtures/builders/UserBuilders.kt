package com.example.fixtures.builders

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserStatus
import com.example.application.domain.UserStatus.ACTIVE
import com.example.application.port.input.UserResponse

const val DEFAULT_USER_ID: String = "A"
const val DEFAULT_USER_NAME: String = "Eli"

fun buildUser(
    userId: String = DEFAULT_USER_ID,
    status: UserStatus = ACTIVE,
) = User(
    UserId(userId),
    Name(DEFAULT_USER_NAME),
    status,
)

fun buildUserResponse(userId: String = DEFAULT_USER_ID) =
    UserResponse(
        userId,
        DEFAULT_USER_NAME,
        ACTIVE,
    )
