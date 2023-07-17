package com.example.fixtures.builders

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserStatus.ACTIVE

const val DEFAULT_USER_ID: String = "A"
const val DEFAULT_USER_NAME: String = "Eli"

fun buildUser() = User(
    UserId(DEFAULT_USER_ID),
    Name(DEFAULT_USER_NAME),
    ACTIVE
)