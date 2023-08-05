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
    status: UserStatus = ACTIVE,
) = User(
    UserId(DEFAULT_USER_ID),
    Name(DEFAULT_USER_NAME),
    status,
)

fun buildUserResponse() = UserResponse(
    DEFAULT_USER_ID,
    DEFAULT_USER_NAME,
    ACTIVE,
)
